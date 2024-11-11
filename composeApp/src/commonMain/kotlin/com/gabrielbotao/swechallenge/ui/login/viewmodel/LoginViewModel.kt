package com.gabrielbotao.swechallenge.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbotao.swechallenge.domain.usecase.LoginState
import com.gabrielbotao.swechallenge.domain.usecase.PostLoginUseCase
import com.gabrielbotao.swechallenge.domain.usecase.SaveLoggedInStatusUseCase
import com.gabrielbotao.swechallenge.ui.login.uistate.LoginUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val postLoginUseCase: PostLoginUseCase,
    private val saveLoggedInStatusUseCase: SaveLoggedInStatusUseCase
) : ViewModel() {

    private val loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.Loading)
    val loginState: StateFlow<LoginUIState> get() = loginUIState

    private var activeScope = createScope()

    private fun createScope() = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun saveLoggedInStatus(isLoggedIn: Boolean) {
        viewModelScope.launch {
            saveLoggedInStatusUseCase.execute(isLoggedIn)
        }
    }

    fun login(username: String, password: String) {
        activeScope.launch {
            postLoginUseCase.execute(
                username = username,
                password = password
            ).collect { state ->
                when (state) {
                    LoginState.Loading -> loginUIState.value = LoginUIState.Loading

                    is LoginState.Success -> {
                        loginUIState.value = LoginUIState.Login(state.data)
                    }

                    LoginState.Error -> loginUIState.value = LoginUIState.Error
                }
            }
        }
    }

    fun logout() {
        loginUIState.value = LoginUIState.Logout
        activeScope.cancel()
        activeScope = createScope()
    }
}