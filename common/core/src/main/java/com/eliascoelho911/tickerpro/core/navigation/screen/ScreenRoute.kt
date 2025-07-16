package com.eliascoelho911.tickerpro.core.navigation.screen

import kotlinx.serialization.Serializable

sealed class ScreenRoute

@Serializable
data object HomeScreenRoute : ScreenRoute()
