package com.gabrielbotao.swechallenge.ui.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.gabrielbotao.swechallenge.PlatFormTypeEnum
import com.gabrielbotao.swechallenge.getPlatform

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    data object Platform : BottomNavItem("platform", getNameFromPlatform(), getIconFromPlatform())
    data object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)

}

private fun getIconFromPlatform(): ImageVector {
    val platform = getPlatform()

    return when (platform.type) {
        PlatFormTypeEnum.IOS -> Icons.Default.Build
        PlatFormTypeEnum.ANDROID -> Icons.Default.Done
        else -> {
            Icons.Default.Warning
        }
    }
}

private fun getNameFromPlatform() = getPlatform().name
