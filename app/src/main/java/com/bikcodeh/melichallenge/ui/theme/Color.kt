package com.bikcodeh.melichallenge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val BitterLemon = Color(0XFFCCCC00)
val CharlestonGreen = Color(0xFF2A2A2A)
val GraniteGray = Color(0xFF656565)
val ShinyShamrock = Color(0xFF55AF85)
val CelticBlue = Color(0xFF2968C8)
val AzureishWhite = Color(0xFFD9E7FA)

val ColorScheme.statusBarColor
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Yellow else BitterLemon

val ColorScheme.backgroundColor
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else CharlestonGreen

val ColorScheme.textColor
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.DarkGray else Color.LightGray

val ColorScheme.backgroundColorTextField
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else Color.Black

val ColorScheme.iconColor
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.DarkGray else Color.White

val ColorScheme.itemListSelected
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.LightGray.copy(alpha = 0.2f) else Color.DarkGray.copy(0.8f)

val ColorScheme.itemListNotSelected
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else CharlestonGreen

val ColorScheme.buttonColor
    @Composable
    get() = if (!isSystemInDarkTheme()) CelticBlue else BitterLemon

val ColorScheme.buttonTexColor
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Black else Color.Black