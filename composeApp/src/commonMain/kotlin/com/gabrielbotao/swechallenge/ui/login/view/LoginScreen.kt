@file:OptIn(KoinExperimentalAPI::class)

package com.gabrielbotao.swechallenge.ui.login.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gabrielbotao.swechallenge.navigation.RoutesEnum
import com.gabrielbotao.swechallenge.ui.login.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val viewModel = koinViewModel<LoginViewModel>()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text("Login Screen")
            Button(
                onClick = {
                    viewModel.saveLoggedInStatus(true)
                    navController.navigate(RoutesEnum.MAIN_SCREEN.key) {
                        popUpTo(RoutesEnum.LOGIN.key) { inclusive = true }
                    }
                }
            ) {
                Text("Go to main screen")
            }
        }
    }
}