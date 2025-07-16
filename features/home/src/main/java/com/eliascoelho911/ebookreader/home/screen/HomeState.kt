package com.eliascoelho911.ebookreader.home.screen

import androidx.compose.runtime.Immutable
import com.eliascoelho911.ebookreader.core.arch.UIState

@Immutable
internal data class HomeState(
    val isLoading: Boolean = true,
) : UIState