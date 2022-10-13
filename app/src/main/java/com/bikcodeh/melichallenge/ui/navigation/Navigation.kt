package com.bikcodeh.melichallenge.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bikcodeh.melichallenge.data.util.Util
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.ui.screens.detail.DetailScreen
import com.bikcodeh.melichallenge.ui.screens.home.HomeScreen
import com.bikcodeh.melichallenge.ui.screens.splash.SplashScreen
import com.bikcodeh.melichallenge.util.extension.decode

@ExperimentalMaterialApi
@Composable
fun MeliNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(route = Screens.Home.route) {
            HomeScreen(
                navigateToDetail = {
                    navController.navigate(Screens.Detail.passItem(it))
                })
        }

        composable(route = Screens.Splash.route) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.Splash.route)  {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screens.Detail.route,
            arguments = listOf(navArgument(Screens.Detail.NAV_ARG_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val item = backStackEntry.arguments?.getString(Screens.Detail.NAV_ARG_KEY)
            item?.decode()?.let {
                Util.fromJson<Product>(it)?.run {
                    DetailScreen(onBack = { navController.popBackStack() }, product = this)
                }
            }
        }
    }
}