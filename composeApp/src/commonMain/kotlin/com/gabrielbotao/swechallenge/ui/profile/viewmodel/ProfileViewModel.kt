package com.gabrielbotao.swechallenge.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.gabrielbotao.swechallenge.domain.usecase.GetUserDataUseCase
import com.gabrielbotao.swechallenge.domain.usecase.UserDataState
import com.gabrielbotao.swechallenge.ui.profile.uistate.ProfileUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {

    private val profileUIState = MutableStateFlow<ProfileUIState>(ProfileUIState.Loading)
    val profileState: StateFlow<ProfileUIState> = profileUIState

    private var activeScope = createScope()

    private fun createScope() = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun getUserData() {
        activeScope = createScope()
        activeScope.launch {
            getUserDataUseCase.execute().collect { state ->
                when (state) {
                    UserDataState.Loading -> profileUIState.value = ProfileUIState.Loading
                    is UserDataState.Success -> profileUIState.value = ProfileUIState.Success(state.data)
                    UserDataState.Error -> profileUIState.value = ProfileUIState.Error
                }
            }
        }
    }

    fun logout() {
        activeScope.cancel()
    }
}