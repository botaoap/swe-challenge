package com.gabrielbotao.swechallenge.ui.login.uistate

import com.gabrielbotao.swechallenge.domain.model.LoginModel

sealed class LoginUIState {
    data object Loading : LoginUIState()
    data class Login(
        val data: LoginModel
    ) : LoginUIState()

    data object Logout : LoginUIState()
    data object Error : LoginUIState()
}