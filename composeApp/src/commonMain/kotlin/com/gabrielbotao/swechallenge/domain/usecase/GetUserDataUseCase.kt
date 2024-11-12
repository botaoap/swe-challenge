package com.gabrielbotao.swechallenge.domain.usecase

import com.gabrielbotao.swechallenge.domain.model.LoginModel
import com.gabrielbotao.swechallenge.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

interface GetUserDataUseCase {
    fun execute(): Flow<UserDataState>
}

class GetUserDataUseCaseImpl(
    private val repository: Repository,
    private val coroutineDispatcher: CoroutineDispatcher
) : GetUserDataUseCase {
    override fun execute(): Flow<UserDataState> = flow {
        try {
            repository.getCurrentUserData().collect { response ->
                println("Key userData(GetUserDataUseCase): $response")
                emit(UserDataState.Success(response))
            }
        } catch (e: Exception) {
            println("Key userData(GetUserDataUseCase): Error")
            emit(UserDataState.Error)
        }
    }.onStart {
        println("Key userData(GetUserDataUseCase): Loading")
        emit(UserDataState.Loading)
    }.flowOn(coroutineDispatcher)

}

sealed class UserDataState {
    data object Loading : UserDataState()
    data class Success(
        val data: LoginModel
    ) : UserDataState()

    data object Error : UserDataState()
}