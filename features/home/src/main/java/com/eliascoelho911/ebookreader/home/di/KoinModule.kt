package com.eliascoelho911.ebookreader.home.di

import com.eliascoelho911.ebookreader.home.screen.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel()
    }
}