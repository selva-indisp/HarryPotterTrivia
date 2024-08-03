package com.indisp.harrypottertrivia

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.indisp.designsystem.theme.DsTheme
import com.indisp.harrypottertrivia.navigation.Route
import com.indisp.harrypottertrivia.search.ui.SearchItemDetailScreen
import com.indisp.harrypottertrivia.search.ui.SearchScreen
import com.indisp.harrypottertrivia.search.ui.SearchViewModel
import org.koin.androidx.compose.koinViewModel

private const val ANIM_DURATION = 300

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DsTheme {
                val navController = rememberNavController()
                val searchViewModel: SearchViewModel = koinViewModel()
                NavHost(
                    navController = navController,
                    startDestination = Route.SEARCH_SCREEN.name,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            tween(ANIM_DURATION)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            tween(ANIM_DURATION)
                        )
                    },
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            tween(ANIM_DURATION)
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            tween(ANIM_DURATION)
                        )
                    },
                ) {
                    composable(Route.SEARCH_SCREEN.name) {
                        SearchScreen(
                            screenStateFlow = searchViewModel.screenStateFlow,
                            sideEffectFlow = searchViewModel.sideEffectFlow,
                            navController = navController,
                            onEvent = searchViewModel::onEvent
                        )
                    }

                    composable(Route.SEARCH_DETAIL.name) {
                        SearchItemDetailScreen(
                            navController = navController,
                            screenStateFlow = searchViewModel.screenStateFlow
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}