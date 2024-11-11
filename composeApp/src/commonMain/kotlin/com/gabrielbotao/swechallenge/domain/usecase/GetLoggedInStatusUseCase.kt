package com.gabrielbotao.swechallenge.domain.usecase

import com.gabrielbotao.swechallenge.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface GetLoggedInStatusUseCase {
    fun execute(): Flow<LoggedInState>
}

class GetLoggedInStatusUseCaseImpl(
    private val repository: Repository,
    private val coroutineDispatcher: CoroutineDispatcher
) : GetLoggedInStatusUseCase {
    override fun execute(): Flow<LoggedInState> = flow {
        try {
            repository.getCurrentLoggedInStatus().map { isLoggedIn ->
                isLoggedIn
            }.collect { response ->
                println("Key isLogged(GetLoggedInStatusUseCase): $response")
                emit(LoggedInState.Success(response))
            }
        } catch (e: Exception) {
            println("Key isLogged(GetLoggedInStatusUseCase): Error $e")
            emit(LoggedInState.Error)
        }
    }.onStart {
        println("Key isLogged(GetLoggedInStatusUseCase): Loading")
        emit(LoggedInState.Loading)
    }.flowOn(coroutineDispatcher)
}

sealed class LoggedInState {
    data object Loading : LoggedInState()
    data class Success(
        val isLoggedIn: Boolean
    ) : LoggedInState()

    data object Error : LoggedInState()
}