package com.eliascoelho911.tickerpro.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eliascoelho911.tickerpro.core.navigation.screen.HomeScreenRoute
import com.eliascoelho911.tickerpro.ds.theme.EbookReaderTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(route: HomeScreenRoute) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(HomeIntent.Init)
    }

    Scaffold { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val currentState = state

            if (currentState.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
            }
        }
    }
}

@Composable
@Preview
private fun HomePreview() {
    EbookReaderTheme {
        HomeScreen()
    }
}