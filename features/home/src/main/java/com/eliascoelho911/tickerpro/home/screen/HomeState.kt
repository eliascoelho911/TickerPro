package com.eliascoelho911.tickerpro.home.screen

import androidx.compose.runtime.Immutable
import com.eliascoelho911.tickerpro.core.arch.UIState

@Immutable
internal data class HomeState(
    val isLoading: Boolean = true,
) : UIState