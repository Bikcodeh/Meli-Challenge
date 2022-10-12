package com.bikcodeh.melichallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.melichallenge.ui.navigation.MeliNavigation
import com.bikcodeh.melichallenge.ui.theme.MeliChallengeTheme
import com.bikcodeh.melichallenge.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            val systemUiController = rememberSystemUiController()
            MeliChallengeTheme {
                systemUiController.setStatusBarColor(
                    color = androidx.compose.material3.MaterialTheme.colorScheme.statusBarColor
                )
                MeliNavigation(navController = navController)
            }
        }
    }
}