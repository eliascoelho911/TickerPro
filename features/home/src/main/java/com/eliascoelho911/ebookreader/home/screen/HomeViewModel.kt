package com.eliascoelho911.ebookreader.home.screen

import com.eliascoelho911.ebookreader.core.arch.viewmodel.BaseViewModel
import com.eliascoelho911.ebookreader.home.repository.HomeRepository

internal class HomeViewModel(
    private val repository: HomeRepository,
    initialState: HomeState = HomeState(),
) : BaseViewModel<HomeAction, HomeState, HomeIntent>(initialState) {

    override fun HomeIntent.onIntent() {
        when (this) {
        }
    }
}