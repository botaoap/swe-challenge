package com.gabrielbotao.swechallenge.ui.initial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbotao.swechallenge.domain.usecase.GetLoggedInStatusUseCase
import com.gabrielbotao.swechallenge.domain.usecase.LoggedInState
import com.gabrielbotao.swechallenge.domain.usecase.SaveLoggedInStatusUseCase
import com.gabrielbotao.swechallenge.ui.initial.uistate.LoggedInGoToFlow
import com.gabrielbotao.swechallenge.ui.initial.uistate.LoggedInUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InitialViewModel(
    private val getLoggedInStatusUseCase: GetLoggedInStatusUseCase,
    private val saveLoggedInStatusUseCase: SaveLoggedInStatusUseCase,
) : ViewModel() {
    private val loggedInUIState = MutableStateFlow<LoggedInUIState>(LoggedInUIState.Loading)
    val loggedInState: StateFlow<LoggedInUIState> = loggedInUIState

    private val goToFlowState = MutableStateFlow<LoggedInGoToFlow>(LoggedInGoToFlow.GoToLoginScreen)
    val goToFlow: StateFlow<LoggedInGoToFlow> = goToFlowState

    fun getLoggedInStatus() {
        viewModelScope.launch {
            getLoggedInStatusUseCase.execute().collect { state ->
                when (state) {
                    LoggedInState.Loading -> loggedInUIState.value = LoggedInUIState.Loading

                    is LoggedInState.Success -> {
                        saveLoggedInStatus(state.isLoggedIn)
                        loggedInUIState.value = LoggedInUIState.Success(state.isLoggedIn)
                    }

                    LoggedInState.Error -> loggedInUIState.value = LoggedInUIState.Error
                }
            }
        }
    }

    fun goFlow(isLoggedIn: Boolean) {
        viewModelScope.launch {
            if (isLoggedIn) {
                goToFlowState.value = LoggedInGoToFlow.GoToMainScreen
            } else {
                goToFlowState.value = LoggedInGoToFlow.GoToLoginScreen
            }
        }
    }

    private fun saveLoggedInStatus(isLoggedIn: Boolean) {
        viewModelScope.launch {
            saveLoggedInStatusUseCase.execute(isLoggedIn)
        }
    }
}