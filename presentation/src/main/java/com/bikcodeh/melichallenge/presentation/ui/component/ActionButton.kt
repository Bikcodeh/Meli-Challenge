package com.bikcodeh.melichallenge.presentation.ui.component

import androidx.annotation.StringRes
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun ActionButton(
    modifier: Modifier,
    buttonColors: ButtonColors,
    @StringRes textButton: Int,
    onClick: () -> Unit,
    isEnabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = buttonColors,
        enabled = isEnabled
    ) {
        Text(text = stringResource(id = textButton))
    }
}