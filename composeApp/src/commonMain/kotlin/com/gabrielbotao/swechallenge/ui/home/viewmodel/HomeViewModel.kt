package com.gabrielbotao.swechallenge.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbotao.swechallenge.domain.model.CategoryGroup
import com.gabrielbotao.swechallenge.domain.model.ProductsModel
import com.gabrielbotao.swechallenge.domain.usecase.GetProductsUseCase
import com.gabrielbotao.swechallenge.domain.usecase.ProductsState
import com.gabrielbotao.swechallenge.ui.home.uistate.ProductsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val productsUIState = MutableStateFlow<ProductsUIState>(ProductsUIState.Loading)
    val productsState: StateFlow<ProductsUIState> = productsUIState

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase.execute().collect { state ->
                when (state) {
                    ProductsState.Loading -> productsUIState.value = ProductsUIState.Loading
                    is ProductsState.Success -> {
                        productsUIState.value = ProductsUIState.Success(
                            data = state.data,
                            categories = groupByCategory(state.data)
                        )
                    }

                    ProductsState.Error -> productsUIState.value = ProductsUIState.Error
                }
            }
        }
    }

    private fun groupByCategory(data: List<ProductsModel>): List<CategoryGroup> =
        data.groupBy {
            it.category
        }.map {
            it.key.mapIndexed { index, c ->
                if (index == 0) c.uppercase() else c.lowercase()
            }.let { configTitle ->
                CategoryGroup(
                    title = configTitle.joinToString(separator = ""),
                    item = it.value
                )
            }
        }
}