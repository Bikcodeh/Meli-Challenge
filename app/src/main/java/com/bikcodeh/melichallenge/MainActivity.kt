package com.bikcodeh.melichallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.bikcodeh.melichallenge.ui.screens.home.HomeScreen
import com.bikcodeh.melichallenge.ui.screens.home.HomeViewModel
import com.bikcodeh.melichallenge.ui.theme.MeliChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeliChallengeTheme {
                HomeScreen(homeViewModel)
            }
        }
    }
}