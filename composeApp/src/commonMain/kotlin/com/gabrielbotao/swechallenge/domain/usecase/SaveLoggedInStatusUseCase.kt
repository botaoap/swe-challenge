package com.gabrielbotao.swechallenge.domain.usecase

import com.gabrielbotao.swechallenge.domain.repository.Repository

interface SaveLoggedInStatusUseCase {
    suspend fun execute(isLoggedIn: Boolean)
}

class SaveLoggedInStatusUseCaseImpl(
    private val repository: Repository
) : SaveLoggedInStatusUseCase {
    override suspend fun execute(isLoggedIn: Boolean) {
        repository.saveLoggedInStatus(isLoggedIn)
    }
}