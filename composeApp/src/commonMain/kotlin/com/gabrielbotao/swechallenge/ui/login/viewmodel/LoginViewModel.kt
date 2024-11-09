package com.gabrielbotao.swechallenge.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbotao.swechallenge.domain.usecase.SaveLoggedInStatusUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val saveLoggedInStatusUseCase: SaveLoggedInStatusUseCase
) : ViewModel() {

    fun saveLoggedInStatus(isLoggedIn: Boolean) {
        viewModelScope.launch {
            saveLoggedInStatusUseCase.execute(isLoggedIn)
        }
    }
}