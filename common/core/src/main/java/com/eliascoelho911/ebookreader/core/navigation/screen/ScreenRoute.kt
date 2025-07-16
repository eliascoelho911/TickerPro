package com.eliascoelho911.ebookreader.core.navigation.screen

import kotlinx.serialization.Serializable

sealed class ScreenRoute

@Serializable
data object HomeScreenRoute : ScreenRoute()
