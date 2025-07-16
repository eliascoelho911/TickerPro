package com.eliascoelho911.tickerpro.home.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class HomeRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
}