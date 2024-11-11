package com.gabrielbotao.swechallenge.ui.bottomnavigation.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gabrielbotao.swechallenge.ui.bottomnavigation.BottomNavItem
import com.gabrielbotao.swechallenge.ui.home.view.HomeScreen
import com.gabrielbotao.swechallenge.ui.platform.view.PlatformScreen
import com.gabrielbotao.swechallenge.ui.profile.view.ProfileScreen

@Composable
fun MainScreen(
    mainNavController: NavHostController
) {
    val bottomNacController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(bottomNacController) }
    ) { innerPadding ->
        NavHost(
            navController = bottomNacController,
            startDestination = BottomNavItem.Home.route
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen(modifier = Modifier.padding(innerPadding)) }
            composable(BottomNavItem.Platform.route) { PlatformScreen() }
            composable(BottomNavItem.Profile.route) { ProfileScreen(navController = mainNavController) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home, BottomNavItem.Platform, BottomNavItem.Profile
    )
    BottomNavigation(
        backgroundColor = Color.Gray,
        contentColor = Color.White
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { destination ->
                                popUpTo(destination) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            )
        }
    }
}