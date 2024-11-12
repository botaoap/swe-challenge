package com.gabrielbotao.swechallenge.ui.profile.uistate

import com.gabrielbotao.swechallenge.domain.model.LoginModel

sealed class ProfileUIState {
    data object Loading : ProfileUIState()
    data class Success(
        val data: LoginModel
    ) : ProfileUIState()

    data object Error : ProfileUIState()
}