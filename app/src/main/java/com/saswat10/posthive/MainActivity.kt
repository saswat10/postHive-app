package com.saswat10.posthive

import android.os.Bundle
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
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saswat10.network.KtorClient
import com.saswat10.posthive.screens.CreateUpdatePost
import com.saswat10.posthive.screens.DiscoverScreen
import com.saswat10.posthive.screens.LoginScreen
import com.saswat10.posthive.screens.ProfileScreen
import com.saswat10.posthive.screens.RegisterScreen
import com.saswat10.posthive.screens.SinglePost
import com.saswat10.posthive.ui.theme.DraculaPink
import com.saswat10.posthive.ui.theme.DraculaYellow
import com.saswat10.posthive.ui.theme.PostHiveTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val items =
                listOf(NavDestination.Discover, NavDestination.CreatePost, NavDestination.Profile)
            var selectedIndex: Int by remember { mutableIntStateOf(0) }
            PostHiveTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
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
                                    colors = NavigationBarItemColors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        selectedIndicatorColor = MaterialTheme.colorScheme.surface,
                                        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                                        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                                        disabledIconColor = DraculaYellow,
                                        disabledTextColor = DraculaPink,
                                    )
                                )

                            }
                        }
                    }
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "discover_screen"
                        ) {
                            composable("discover_screen") {
                                DiscoverScreen(navController = navController, onClicked = {
                                    navController.navigate("post_detail/$it")
                                })
                            }
                            composable(route = NavDestination.CreatePost.route) {
                                CreateUpdatePost(navController = navController)
                            }
                            composable(route = NavDestination.Profile.route) {
                                ProfileScreen(navController = navController)
                            }
                            composable(
                                route = "post_detail/{postId}", arguments = listOf(
                                    navArgument("postId") {
                                        type = NavType.IntType
                                    }
                                )
                            ) { backStackEntry ->
                                val postId: Int = backStackEntry.arguments?.getInt("postId")
                                    ?: -1
                                SinglePost(
                                    navController = navController,
                                    postId = postId,
                                    onBackClicked = {
                                        navController.navigateUp()
                                    })
                            }
                            composable(route = "login") {
                                LoginScreen(navController = navController, onButtonClicked = {
                                    navController.navigate(
                                        "register"
                                    )
                                })
                            }
                            composable(route = "login") {
                                RegisterScreen(navController = navController, onButtonClicked = {
                                    navController.navigate(
                                        "register"
                                    )
                                })
                            }
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
        NavDestination("Create Post", route = "create_screen", Icons.Rounded.AddCircle)

    object Profile : NavDestination("Profile", route = "profile", Icons.Rounded.AccountCircle)
}