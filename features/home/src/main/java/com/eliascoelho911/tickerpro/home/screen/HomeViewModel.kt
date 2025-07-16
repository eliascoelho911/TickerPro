package com.eliascoelho911.tickerpro.home.screen

import com.eliascoelho911.tickerpro.core.arch.viewmodel.BaseViewModel
import com.eliascoelho911.tickerpro.home.repository.HomeRepository

internal class HomeViewModel(
    private val repository: HomeRepository,
    initialState: com.eliascoelho911.tickerpro.home.screen.HomeState = com.eliascoelho911.tickerpro.home.screen.HomeState(),
) : BaseViewModel<HomeAction, com.eliascoelho911.tickerpro.home.screen.HomeState, HomeIntent>(initialState) {

    override fun HomeIntent.onIntent() {
        when (this) {
        }
    }
}