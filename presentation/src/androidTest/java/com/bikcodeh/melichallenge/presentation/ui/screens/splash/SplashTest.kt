package com.bikcodeh.melichallenge.presentation.ui.screens.splash

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@ExperimentalMaterialApi
@ExperimentalTestApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SplashScreenTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun assert_splash_screen_is_properly_displayed() {
        composeTestRule.setContent {
            SplashScreen(navigateToHome = {})
        }
        composeTestRule.onNodeWithTag(SplashTestTags.SPLASH_CONTAINER).assertExists()
        composeTestRule.onNodeWithTag(SplashTestTags.SPLASH_CONTAINER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SplashTestTags.SPLASH_LOTTIE).assertExists()
        composeTestRule.onNodeWithTag(SplashTestTags.SPLASH_LOTTIE).assertIsDisplayed()
    }
}