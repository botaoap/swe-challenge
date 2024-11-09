@file:OptIn(KoinExperimentalAPI::class)

package com.gabrielbotao.swechallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gabrielbotao.swechallenge.ui.bottomnavigation.view.MainScreen
import com.gabrielbotao.swechallenge.ui.initial.uistate.LoggedInGoToFlow
import com.gabrielbotao.swechallenge.ui.initial.uistate.LoggedInUIState
import com.gabrielbotao.swechallenge.ui.initial.viewmodel.InitialViewModel
import com.gabrielbotao.swechallenge.ui.login.view.LoginScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun MainNavGraph() {
    val mainNavController = rememberNavController()
    val viewModel = koinViewModel<InitialViewModel>()

    LaunchedEffect(true) {
        viewModel.getLoggedInStatus()
        println("Key isLogged(getLoggedInStatus): ----")
    }

    viewModel.loggedInState.collectAsStateWithLifecycle().let { state ->
        state.value.let {
            when (it) {
                LoggedInUIState.Loading -> {
                    // TODO: do some loading effects
                    println("Key isLogged(Loading): loading")
                }

                is LoggedInUIState.Success -> {
                    println("Key isLogged(Success): ${it.isLoggedIn}")
                    viewModel.goFlow(it.isLoggedIn)
                }

                LoggedInUIState.Error -> {
                    // TODO: do error scenario
                    println("Key isLogged(Error): error")
                }
            }
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
        composable(route = RoutesEnum.LOGIN.key) { LoginScreen(mainNavController) }
        composable(route = RoutesEnum.MAIN_SCREEN.key) { MainScreen(mainNavController) }
    }
}