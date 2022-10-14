package com.bikcodeh.melichallenge.presentation.ui.component

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeDefaults
import com.bikcodeh.melichallenge.presentation.ui.theme.COMMON_PADDING
import com.bikcodeh.melichallenge.presentation.ui.theme.backgroundColor
import com.bikcodeh.melichallenge.presentation.ui.theme.textColor

@Composable
fun GenericMessageScreen(
    modifier: Modifier = Modifier,
    @RawRes lottieId: Int,
    @StringRes messageId: Int
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieId))
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.backgroundColor)
            .fillMaxSize()
            .padding(COMMON_PADDING)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition, modifier = Modifier.size(HomeDefaults.EMPTY_PRODUCTS_LOTTIE_SIZE))
        Text(
            text = stringResource(id = messageId),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.textColor
        )
    }
}