package com.example.myborutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.myborutoapp.presentation.screens.home.HomeScreen
import com.example.myborutoapp.presentation.screens.splash.SplashScreen
import com.example.myborutoapp.presentation.screens.welcome.WelcomeScreen
import com.example.myborutoapp.util.Constants.DETAILS_ARGUMENT_KEY

@ExperimentalCoilApi
@Composable
fun SetUpNavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navHostController = navHostController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navHostController = navHostController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument(name = DETAILS_ARGUMENT_KEY) {
                    type = NavType.IntType
                }
            )
        ) {

        }
        composable(route = Screen.Search.route) {

        }
    }
}
