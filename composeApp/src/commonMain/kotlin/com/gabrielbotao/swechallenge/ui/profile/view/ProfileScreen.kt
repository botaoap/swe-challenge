@file:OptIn(KoinExperimentalAPI::class)

package com.gabrielbotao.swechallenge.ui.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gabrielbotao.swechallenge.navigation.RoutesEnum
import com.gabrielbotao.swechallenge.ui.profile.viewmodel.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel = koinViewModel<ProfileViewModel>()

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Profile Screen")
        Button(
            onClick = {
                viewModel.saveLoggedInStatus(false)
                navController.navigate(RoutesEnum.LOGIN.key) {
                    popUpTo(RoutesEnum.MAIN_SCREEN.key) { inclusive = true }
                }
            }
        ) {
            Text("Logout")
        }
    }
}