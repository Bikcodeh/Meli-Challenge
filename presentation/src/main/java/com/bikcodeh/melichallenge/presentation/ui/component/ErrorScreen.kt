package com.bikcodeh.melichallenge.presentation.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.*
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.theme.*
import com.bikcodeh.melichallenge.domain.R as RD
import com.bikcodeh.melichallenge.domain.common.Error as ErrorDomain

const val ERROR_CONTAINER = "ErrorContainer"
const val ERROR_MESSAGE = "ErrorMessage"
const val ERROR_BUTTON = "ErrorButton"

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    @StringRes messageId: Int,
    onTryAgain: () -> Unit,
    displayTryButton: Boolean
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        modifier = modifier
            .testTag(ERROR_CONTAINER)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.backgroundColor)
            .padding(COMMON_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition,
            progress = { progress },
            modifier = Modifier
                .size(ERROR_LOTTIE_SIZE)
        )
        Text(
            text = stringResource(id = messageId),
            fontSize = FONT_SIZE_ERROR_MESSAGE,
            color = MaterialTheme.colorScheme.textColor,
            modifier = Modifier.fillMaxWidth().testTag(ERROR_MESSAGE),
            textAlign = TextAlign.Center
        )
        if (displayTryButton) {
            OutlinedButton(onClick = onTryAgain, modifier = Modifier
                .testTag(ERROR_BUTTON)
                .fillMaxWidth()
                .padding(vertical = COMMON_PADDING)) {
                Text(text = stringResource(id = RD.string.try_again))
            }
        }
    }
}

@Composable
fun handleError(error: ErrorDomain): Int {
    return when (error) {
        ErrorDomain.Connectivity -> RD.string.connectivity_error
        ErrorDomain.InternetConnection -> RD.string.internet_error
        is ErrorDomain.HttpException -> error.messageResId
        is ErrorDomain.Unknown -> RD.string.unknown_error
    }
}

@Preview(heightDp = 600)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        messageId = RD.string.unknown_error,
        onTryAgain = {},
        displayTryButton = true
    )
}

@Preview(heightDp = 600, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ErrorScreenPreviewDark() {
    ErrorScreen(
        messageId = RD.string.unknown_error,
        onTryAgain = {},
        displayTryButton = true
    )
}