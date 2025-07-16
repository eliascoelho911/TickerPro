package com.eliascoelho911.tickerpro.core.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import com.eliascoelho911.tickerpro.core.navigation.screen.ScreenRoute

class NavigationController(private val navController: NavController) {
    fun navigateTo(route: ScreenRoute) {
        navController.navigate(route)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}

val LocalNavigationController = compositionLocalOf<NavigationController> {
    error("No NavigationController provided")
}
