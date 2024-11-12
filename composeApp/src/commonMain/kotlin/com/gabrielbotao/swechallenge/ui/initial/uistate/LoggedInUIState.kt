package com.gabrielbotao.swechallenge.ui.initial.uistate

sealed class LoggedInUIState {
    data object Loading : LoggedInUIState()
    data class Success(
        val isLoggedIn: Boolean
    ) : LoggedInUIState()

    data object Error : LoggedInUIState()
}

sealed class LoggedInGoToFlow {
    data object Loading : LoggedInGoToFlow()
    data object GoToMainScreen : LoggedInGoToFlow()
    data object GoToLoginScreen : LoggedInGoToFlow()
}