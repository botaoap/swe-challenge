@file:OptIn(KoinExperimentalAPI::class)

package com.gabrielbotao.swechallenge

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.gabrielbotao.swechallenge.navigation.MainNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainNavGraph()
    }
}