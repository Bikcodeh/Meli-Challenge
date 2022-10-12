package com.bikcodeh.melichallenge.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bikcodeh.melichallenge.R
import com.bikcodeh.melichallenge.ui.theme.TOP_APP_BAR_HEIGHT
import com.bikcodeh.melichallenge.ui.theme.statusBarColor
import androidx.compose.material3.MaterialTheme as MaterialTheme3

@Composable
fun DetailTopBar(onBack: () -> Unit) {
    Surface(
        color = MaterialTheme3.colorScheme.statusBarColor, modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT)
            .background(MaterialTheme3.colorScheme.statusBarColor)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (backButton, title) = createRefs()
            IconButton(onClick = { onBack() }, modifier = Modifier.constrainAs(backButton) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color.Black
                )
            }
            Text(
                text = stringResource(id = R.string.detail_title),
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Composable
@Preview()
fun DetailTopBarPreview() {
    DetailTopBar(onBack = {})
}