@file:OptIn(KoinExperimentalAPI::class)

package com.gabrielbotao.swechallenge.ui.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gabrielbotao.swechallenge.navigation.RoutesEnum
import com.gabrielbotao.swechallenge.ui.login.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val loginViewModel = koinViewModel<LoginViewModel>()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Button(
                onClick = {
                    loginViewModel.logout()
                    navController.navigate(RoutesEnum.LOGIN.key) {
                        popUpTo(RoutesEnum.MAIN_SCREEN.key) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Gray)
            ) {
                Text(
                    color = Color.White,
                    text = "Logout"
                )
            }
        }
    }
}