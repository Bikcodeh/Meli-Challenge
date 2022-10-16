package com.bikcodeh.melichallenge.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.ui.theme.COMMON_PADDING
import com.bikcodeh.melichallenge.presentation.ui.theme.ERROR_LOTTIE_SIZE
import com.bikcodeh.melichallenge.presentation.ui.theme.backgroundColor
import com.bikcodeh.melichallenge.presentation.ui.theme.textColor
import com.bikcodeh.melichallenge.presentation.ui.util.BaseViewModel
import com.bikcodeh.melichallenge.domain.R as RD

const val ERROR_CONTAINER = "ErrorContainer"
const val ERROR_MESSAGE = "ErrorMessage"
const val ERROR_BUTTON = "ErrorButton"

@Composable
fun ErrorScreen(error: BaseViewModel.Error, onRefresh: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))

    ConstraintLayout(
        modifier = Modifier
            .testTag(ERROR_CONTAINER)
            .background(MaterialTheme.colorScheme.backgroundColor)
            .fillMaxSize()
            .padding(COMMON_PADDING)
            .verticalScroll(rememberScrollState())
    ) {
        val (lottie, message, button) = createRefs()
        LottieAnimation(
            composition,
            modifier = Modifier
                .size(ERROR_LOTTIE_SIZE)
                .constrainAs(lottie) {
                    linkTo(parent.top, parent.bottom, bias = 0.4f)
                    linkTo(parent.start, parent.end)
                })
        Text(
            text = stringResource(id = error.errorMessage ?: RD.string.unknown_error),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .testTag(ERROR_MESSAGE)
                .fillMaxWidth()
                .padding(horizontal = COMMON_PADDING)
                .constrainAs(message) {
                    top.linkTo(lottie.bottom, margin = COMMON_PADDING)
                },
            color = MaterialTheme.colorScheme.textColor,
            fontSize = 18.sp
        )

        OutlinedButton(onClick = onRefresh, modifier = Modifier
            .testTag(ERROR_BUTTON)
            .fillMaxWidth()
            .constrainAs(button) {
                bottom.linkTo(parent.bottom, margin = COMMON_PADDING)
            }) {
            Text(text = stringResource(id = RD.string.try_again))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(error = BaseViewModel.Error(), onRefresh = { })
}