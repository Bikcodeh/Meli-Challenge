package com.bikcodeh.melichallenge.presentation.ui.screens.detail

import android.os.SystemClock
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.presentation.MainActivity
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.base.BaseUITest
import com.bikcodeh.melichallenge.presentation.network.FILE_SUCCESS_DETAIL_DESCRIPTION_RESPONSE
import com.bikcodeh.melichallenge.presentation.network.mockResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.HttpURLConnection

@HiltAndroidTest
class DetailScreenTest : BaseUITest(dispatcher = detailDispatcher) {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
        setView()
    }

    private fun setView() {
        composeTestRule.activity.setContent {
            DetailScreen(
                product = Product(
                    id = "",
                    title = "Hello world",
                    price = 99999.0,
                    availableQuantity = 5,
                    soldQuantity = 125,
                    condition = "New",
                    thumbnail = ""
                ), onBack = {}, detailViewModel = hiltViewModel()
            )
        }
    }

    @Test
    fun assert_screen_is_properly_displayed() {
        typeRequest = TypeRequest.NoError
        composeTestRule.onNodeWithTag(DetailTestTags.CONTAINER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.TITLE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.CONDITION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.BUTTON_BUY_NOW).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.BUTTON_ADD_TO_CART).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.CONTAINER).assert(hasScrollAction())
        composeTestRule.onNodeWithTag(DetailTestTags.CONTAINER).onChildren()
            .assertAny(hasText("This is the description test"))
    }

    @Test
    fun assert_screen_is_properly_displayed_without_description() {
        typeRequest = TypeRequest.Error
        composeTestRule.onNodeWithTag(DetailTestTags.CONTAINER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.TITLE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.CONDITION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.BUTTON_BUY_NOW).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.BUTTON_ADD_TO_CART).assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailTestTags.CONTAINER).assert(hasScrollAction())
        composeTestRule.onNodeWithTag(DetailTestTags.CONTAINER).onChildren()
            .assertAny(hasTextExactly(composeTestRule.activity.getString(R.string.error_description)))
    }
}

private sealed class TypeRequest {
    object NoError : TypeRequest()
    object Error : TypeRequest()
}

private var typeRequest: TypeRequest = TypeRequest.NoError

private val detailDispatcher by lazy {
    object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (typeRequest) {
                TypeRequest.Error -> MockResponse().apply {
                    setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
                }
                TypeRequest.NoError -> mockResponse(
                    fileName = FILE_SUCCESS_DETAIL_DESCRIPTION_RESPONSE,
                    responseCode = HttpURLConnection.HTTP_OK,
                )
            }
        }
    }
}
