package com.bikcodeh.melichallenge.presentation.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.screens.splash.SplashTestTags.SPLASH_CONTAINER
import com.bikcodeh.melichallenge.presentation.ui.screens.splash.SplashTestTags.SPLASH_LOTTIE
import com.bikcodeh.melichallenge.presentation.ui.theme.backgroundColor
import kotlinx.coroutines.delay

object SplashTestTags {
    const val SPLASH_CONTAINER = "SplashContainer"
    const val SPLASH_LOTTIE = "SplashLottie"
}

@Composable
fun SplashScreen(navigateToHome: () -> Unit) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.shopping))

    Column(
        modifier = Modifier
            .testTag(SPLASH_CONTAINER)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition,
            modifier = Modifier.testTag(SPLASH_LOTTIE)
        )

        LaunchedEffect(key1 = true) {
            delay(1000)
            navigateToHome()
        }
    }
}