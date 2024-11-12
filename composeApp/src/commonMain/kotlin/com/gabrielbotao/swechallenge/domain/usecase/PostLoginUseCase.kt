package com.gabrielbotao.swechallenge.domain.usecase

import com.gabrielbotao.swechallenge.domain.mapper.LoginMapper
import com.gabrielbotao.swechallenge.domain.model.LoginModel
import com.gabrielbotao.swechallenge.domain.repository.Repository
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

interface PostLoginUseCase {
    fun execute(username: String, password: String): Flow<LoginState>
}

class PostLoginUseCaseImpl(
    private val repository: Repository,
    private val mapper: LoginMapper,
    private val coroutineDispatcher: CoroutineDispatcher
) : PostLoginUseCase {
    override fun execute(username: String, password: String): Flow<LoginState> = flow {
        try {
            repository.postLogin(
                username = username,
                password = password
            ).let { response ->
                when (response.status.value) {
                    200 -> {
                        println("Key isLogged(PostLoginUseCase): ${response.bodyAsText()}")
                        emit(LoginState.Success(mapper.getLogin(response.body())))
                    }

                    else -> {
                        println("Key isLogged(PostLoginUseCase): ${response.bodyAsText()}")
                        emit(LoginState.Error)
                    }
                }
            }
        } catch (e: Exception) {
            println("Key isLogged(PostLoginUseCase): $e")
            emit(LoginState.Error)
        }
    }.onStart {
        println("Key isLogged(PostLoginUseCase): Loading")
        emit(LoginState.Loading)
    }.flowOn(coroutineDispatcher)
}

sealed class LoginState {
    data object Loading : LoginState()
    data class Success(
        val data: LoginModel
    ) : LoginState()

    data object Error : LoginState()
}