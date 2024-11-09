package com.gabrielbotao.swechallenge

import androidx.compose.ui.window.ComposeUIViewController
import com.gabrielbotao.swechallenge.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}