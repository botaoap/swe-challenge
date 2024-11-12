@file:OptIn(KoinExperimentalAPI::class)

package com.gabrielbotao.swechallenge.ui.profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.gabrielbotao.swechallenge.components.ErrorComponent
import com.gabrielbotao.swechallenge.components.LoadingComponent
import com.gabrielbotao.swechallenge.domain.model.LoginModel
import com.gabrielbotao.swechallenge.navigation.RoutesEnum
import com.gabrielbotao.swechallenge.ui.login.viewmodel.LoginViewModel
import com.gabrielbotao.swechallenge.ui.profile.uistate.ProfileUIState
import com.gabrielbotao.swechallenge.ui.profile.viewmodel.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val loginViewModel = koinViewModel<LoginViewModel>()
    val dataState = viewModel.profileState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserData()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        dataState.value.let { state ->
            when (state) {
                ProfileUIState.Loading -> LoadingComponent()

                is ProfileUIState.Success ->
                    LayoutProfile(
                        modifier = modifier,
                        data = state.data
                    ) {
                        loginViewModel.logout()
                        viewModel.logout()
                        navController.navigate(RoutesEnum.LOGIN.key) {
                            popUpTo(RoutesEnum.MAIN_SCREEN.key) { inclusive = true }
                        }
                    }

                ProfileUIState.Error -> ErrorComponent(state.toString()) { viewModel.getUserData() }
            }
        }
    }
}

@Composable
fun LayoutProfile(
    modifier: Modifier,
    data: LoginModel,
    onClickLogout: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        Box(modifier.size(56.dp))
        Image(
            painter = rememberAsyncImagePainter(model = data.image),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .requiredSize(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.LightGray)
        )
        Text(data.username)
        Text(data.fullName)
        Text(data.email)
        Button(
            onClick = {
                onClickLogout.invoke()
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