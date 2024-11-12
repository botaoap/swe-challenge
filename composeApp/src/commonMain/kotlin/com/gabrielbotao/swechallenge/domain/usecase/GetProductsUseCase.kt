package com.gabrielbotao.swechallenge.domain.usecase

import com.gabrielbotao.swechallenge.domain.mapper.ProductsMapper
import com.gabrielbotao.swechallenge.domain.model.ProductsModel
import com.gabrielbotao.swechallenge.domain.repository.Repository
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

interface GetProductsUseCase {
    fun execute(): Flow<ProductsState>
}

class GetProductsUseCaseImpl(
    private val repository: Repository,
    private val mapper: ProductsMapper,
    private val coroutineDispatcher: CoroutineDispatcher
) : GetProductsUseCase {
    override fun execute(): Flow<ProductsState> = flow {
        try {
            repository.getProducts().let { response ->
                when (response.status.value) {
                    200 -> {
                        println("Key isLogged(GetProductsUseCase): ${response.bodyAsText()}")
                        emit(ProductsState.Success(mapper.getProducts(response.body())))
                    }

                    else -> {
                        println("Key isLogged(GetProductsUseCase): ${response.bodyAsText()}")
                        emit(ProductsState.Error)
                    }
                }
            }
        } catch (e: Exception) {
            println("Key isLogged(GetProductsUseCase): $e")
            emit(ProductsState.Error)
        }
    }.onStart {
        println("Key isLogged(GetProductsUseCase): Loading")
        emit(ProductsState.Loading)
    }.flowOn(coroutineDispatcher)

}

sealed class ProductsState {
    data object Loading : ProductsState()
    data class Success(
        val data: List<ProductsModel>
    ) : ProductsState()

    data object Error : ProductsState()
}