package com.gabrielbotao.swechallenge.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbotao.swechallenge.domain.usecase.SaveLoggedInStatusUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val saveLoggedInStatusUseCase: SaveLoggedInStatusUseCase
) : ViewModel() {

    fun saveLoggedInStatus(isLoggedIn: Boolean) {
        viewModelScope.launch {
            saveLoggedInStatusUseCase.execute(isLoggedIn)
        }
    }
}