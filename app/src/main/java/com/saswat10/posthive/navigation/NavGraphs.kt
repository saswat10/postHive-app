package com.saswat10.posthive.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.saswat10.posthive.screens.CreateUpdatePost
import com.saswat10.posthive.screens.DiscoverScreen
import com.saswat10.posthive.screens.LoginScreen
import com.saswat10.posthive.screens.ProfileScreen
import com.saswat10.posthive.screens.RegisterScreen
import com.saswat10.posthive.screens.SinglePost

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(startDestination = "login", route = "auth") {
        composable("login") {
            LoginScreen(navController = navController, onButtonClicked = {
                navController.navigate("register")
            })
        }
        composable("register") {
            RegisterScreen(navController = navController, onButtonClicked = {
                navController.navigate("login")
            })
        }
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(startDestination = "discover_screen", route = "main_app") {
        composable("discover_screen") {
            DiscoverScreen(navController = navController, onClicked = {
                navController.navigate("post_detail/$it")
            })
        }
        composable("create_post") {
            CreateUpdatePost(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("post_detail/{postId}", arguments = listOf(
                navArgument("postId"){
                    type = NavType.IntType
                })) { backStackEntry ->
            val postId: Int = backStackEntry.arguments?.getInt("postId") ?: -1
            SinglePost(navController = navController, postId = postId, onBackClicked = {
                navController.navigateUp()
            })
        }
    }
}