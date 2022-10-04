package com.example.myborutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myborutoapp.util.Constants.DETAILS_ARGUMENT_KEY

@Composable
fun SetUpNavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Welcome.route) {

        }
        composable(route = Screen.Home.route) {

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
