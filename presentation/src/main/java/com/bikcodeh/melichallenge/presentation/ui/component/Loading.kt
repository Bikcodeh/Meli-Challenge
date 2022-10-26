package com.bikcodeh.melichallenge.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.*
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.theme.COMMON_PADDING

const val LOADING_CONTAINER = "LoadingContainer"

@Composable
fun Loading() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.searching))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        modifier = Modifier
            .testTag(LOADING_CONTAINER)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(COMMON_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition,
            progress = { progress }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    Loading()
}