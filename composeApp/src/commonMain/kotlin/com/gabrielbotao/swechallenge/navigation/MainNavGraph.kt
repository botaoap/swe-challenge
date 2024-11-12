@file:OptIn(KoinExperimentalAPI::class)

package com.gabrielbotao.swechallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gabrielbotao.swechallenge.components.ErrorComponent
import com.gabrielbotao.swechallenge.components.LoadingComponent
import com.gabrielbotao.swechallenge.ui.bottomnavigation.view.MainScreen
import com.gabrielbotao.swechallenge.ui.initial.uistate.LoggedInGoToFlow
import com.gabrielbotao.swechallenge.ui.initial.uistate.LoggedInUIState
import com.gabrielbotao.swechallenge.ui.initial.viewmodel.InitialViewModel
import com.gabrielbotao.swechallenge.ui.login.view.LoginScreen
import com.gabrielbotao.swechallenge.util.Util
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun MainNavGraph() {
    val mainNavController = rememberNavController()
    val viewModel = koinViewModel<InitialViewModel>()
    val dataState = viewModel.loggedInState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (Util.resetCountOnDestroy == 0) {
            Util.resetCountOnDestroy++
            viewModel.getLoggedInStatus()
        }
    }

    dataState.value.let { state ->
        when (state) {
            LoggedInUIState.Loading -> LoadingComponent()

            is LoggedInUIState.Success -> {
                LoadingComponent()
                if (Util.resetCountOnDestroy == 1) {
                    Util.resetCountOnDestroy++
                    viewModel.goFlow(state.isLoggedIn)
                }
            }

            LoggedInUIState.Error -> ErrorComponent(
                errorMessage = state.toString(),
                onClickTryAgain = {
                    viewModel.goFlow(false)
                }
            )
        }
    }

    viewModel.goToFlow.collectAsStateWithLifecycle().value.let { state ->
        when (state) {
            LoggedInGoToFlow.GoToMainScreen -> {
                startSection(RoutesEnum.MAIN_SCREEN.key, mainNavController)
            }

            LoggedInGoToFlow.GoToLoginScreen -> {
                startSection(RoutesEnum.LOGIN.key, mainNavController)
            }

            LoggedInGoToFlow.Loading -> startSection(RoutesEnum.LOADING.key, mainNavController)
        }
    }
}

@Composable
private fun startSection(
    startDestination: String,
    mainNavController: NavHostController
) {
    NavHost(
        navController = mainNavController,
        startDestination = startDestination
    ) {
        composable(route = RoutesEnum.LOADING.key) { LoadingComponent() }
        composable(route = RoutesEnum.LOGIN.key) { LoginScreen(mainNavController) }
        composable(route = RoutesEnum.MAIN_SCREEN.key) { MainScreen(mainNavController) }
    }
}