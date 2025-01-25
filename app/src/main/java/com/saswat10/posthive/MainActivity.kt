package com.saswat10.posthive

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saswat10.network.KtorClient
import com.saswat10.posthive.di.DataStorage
import com.saswat10.posthive.navigation.authNavGraph
import com.saswat10.posthive.navigation.mainNavGraph
import com.saswat10.posthive.ui.theme.PostHiveTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    @Inject
    lateinit var dataStorage: DataStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val startDestination = remember { mutableStateOf("auth") }
            val currentBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry.value?.destination?.route
            val items =
                listOf(NavDestination.Discover, NavDestination.CreatePost, NavDestination.Profile)
            var selectedIndex: Int by remember { mutableIntStateOf(0) }

            LaunchedEffect(Unit) {
                val token = dataStorage.getBearerToken()
                startDestination.value = if (token.isNullOrEmpty()) "auth" else "main_app"
            }

            val mainRoutes =
                listOf("discover_screen", "create_post", "profile", "post_detail/{postId}")
            val shouldShowBottomBar = currentRoute in mainRoutes



            PostHiveTheme {

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            NavigationBar(
                                containerColor = Color(
                                    ColorUtils.blendARGB(
                                        MaterialTheme.colorScheme.background.toArgb(),
                                        MaterialTheme.colorScheme.surface.toArgb(),
                                        0.4f
                                    )
                                )
                            ) {
                                items.forEachIndexed { index, screen ->
                                    NavigationBarItem(
                                        icon = { Icon(screen.icon, null) },
                                        label = { Text(screen.title) },
                                        selected = index == selectedIndex,
                                        onClick = {
                                            selectedIndex = index
                                            navController.navigate(screen.route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }

                                        },
                                    )

                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = startDestination.value
                        ) {
                            Log.d("route", currentRoute ?: "")
                            authNavGraph(navController)
                            mainNavGraph(navController)
                        }
                    }
                }
            }
        }
    }
}

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector) {
    object Discover : NavDestination("Discover", "discover_screen", Icons.Rounded.PlayArrow)
    object CreatePost :
        NavDestination("Create Post", route = "create_post", Icons.Rounded.AddCircle)

    object Profile : NavDestination("Profile", route = "profile", Icons.Rounded.AccountCircle)
}