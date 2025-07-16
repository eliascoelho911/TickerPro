package com.eliascoelho911.ebookreader.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.eliascoelho911.ebookreader.core.navigation.LocalNavigationController
import com.eliascoelho911.ebookreader.core.navigation.NavigationController
import com.eliascoelho911.ebookreader.core.navigation.screen.HomeScreenRoute
import com.eliascoelho911.ebookreader.core.navigation.screen.LibraryScreenRoute
import com.eliascoelho911.ebookreader.core.navigation.screen.ReaderScreenRoute
import com.eliascoelho911.ebookreader.ds.theme.EbookReaderTheme
import com.eliascoelho911.logs.ComposeLogger
import com.eliascoelho911.logs.LocalComposeLogger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CompositionLocalProvider(
                LocalNavigationController provides NavigationController(navController)
            ) {
                EbookReaderTheme {
                    NavHost(
                        navController = navController,
                        startDestination = HomeScreenRoute
                    ) {
                        composable<HomeScreenRoute> {
                            CompositionLocalProvider(
                                LocalComposeLogger provides ComposeLogger(HomeScreenRoute::class.java.simpleName)
                            ) {
//                                HomeScreen(it.toRoute())
                            }
                        }
                    }
                }
            }
        }
    }
}