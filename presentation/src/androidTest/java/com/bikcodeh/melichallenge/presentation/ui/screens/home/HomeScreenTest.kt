package com.bikcodeh.melichallenge.presentation.ui.screens.home

import android.os.SystemClock
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.melichallenge.presentation.MainActivity
import com.bikcodeh.melichallenge.presentation.R
import com.bikcodeh.melichallenge.presentation.base.BaseUITest
import com.bikcodeh.melichallenge.presentation.network.FILE_SUCCESS_EMPTY_RESPONSE
import com.bikcodeh.melichallenge.presentation.network.FILE_SUCCESS_SEARCH_RESPONSE
import com.bikcodeh.melichallenge.presentation.network.mockResponse
import com.bikcodeh.melichallenge.presentation.ui.component.ERROR_CONTAINER
import com.bikcodeh.melichallenge.presentation.ui.component.ERROR_MESSAGE
import com.bikcodeh.melichallenge.presentation.ui.component.LOADING_CONTAINER
import com.bikcodeh.melichallenge.presentation.ui.component.SearchTestTags
import com.bikcodeh.melichallenge.presentation.ui.navigation.MeliNavigation
import com.bikcodeh.melichallenge.presentation.ui.screens.detail.DetailTestTags
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeTestTags.CONTAINER_PRODUCTS
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeTestTags.ITEM_CONTAINER
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeTestTags.MESSAGE_GENERIC_SCREEN
import com.bikcodeh.melichallenge.presentation.ui.screens.splash.SPLASH_CONTAINER
import com.bikcodeh.melichallenge.presentation.util.waitUntilDoesNotExist
import com.bikcodeh.melichallenge.presentation.util.waitUntilExists
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import org.junit.*
import org.junit.runners.MethodSorters
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import com.bikcodeh.melichallenge.domain.R as RD

@ExperimentalMaterialApi
@ExperimentalTestApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HomeScreenTest : BaseUITest(dispatcher = searchDispatcher) {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
        setView()
    }

    @After
    fun tear_down() {
        setView()
        typeRequest = TypeRequest.Idle
    }

    private fun setView() {
        composeTestRule.activity.setContent {
            MeliNavigation(rememberNavController())
        }
    }

    @Test
    fun assert_a_server_error_screen_is_displayed() {
        typeRequest = TypeRequest.ServerError
        composeTestRule.waitUntilDoesNotExist(hasTestTag(SPLASH_CONTAINER), timeoutMillis = 1100)
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("moa")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(LOADING_CONTAINER))
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertDoesNotExist()
        composeTestRule.onNodeWithTag(ERROR_MESSAGE)
            .assertTextContains(composeTestRule.activity.getString(RD.string.internal_server_error))
    }

    @Test
    fun assert_initial_state_is_displayed() {
        composeTestRule.waitUntilDoesNotExist(hasTestTag(SPLASH_CONTAINER), timeoutMillis = 1100)
        composeTestRule.onNodeWithTag(HomeTestTags.INITIAL_SCREEN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(MESSAGE_GENERIC_SCREEN)
            .assertTextContains(composeTestRule.activity.getString(R.string.search_description))
    }

    @Test
    fun assert_search_bar_initial_state_is_displayed() {
        composeTestRule.waitUntilDoesNotExist(hasTestTag(SPLASH_CONTAINER), timeoutMillis = 1100)
        composeTestRule.onNodeWithTag(SearchTestTags.CONTAINER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT)
            .assertTextContains(composeTestRule.activity.getString(R.string.search_in_meli))
        SystemClock.sleep(1000)
    }

    @Test
    fun assert_products_are_displayed() {
        typeRequest = TypeRequest.NoError
        composeTestRule.waitUntilDoesNotExist(hasTestTag(SPLASH_CONTAINER), timeoutMillis = 1100)
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("motorola")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertIsDisplayed()
        composeTestRule.waitUntilExists(hasTestTag(CONTAINER_PRODUCTS))
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertDoesNotExist()
    }

    @Test
    fun assert_products_are_displayed_and_has_scroll() {
        typeRequest = TypeRequest.NoError
        composeTestRule.waitUntilDoesNotExist(hasTestTag(SPLASH_CONTAINER), timeoutMillis = 1100)
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("motorola")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.waitUntilExists(hasTestTag(LOADING_CONTAINER), timeoutMillis = 2500)
        composeTestRule.waitUntilExists(hasTestTag(CONTAINER_PRODUCTS), timeoutMillis = 2500)
        composeTestRule.onNodeWithTag(CONTAINER_PRODUCTS).assert(hasScrollAction())
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertDoesNotExist()
    }

    @Test
    fun assert_empty_products_screen_is_displayed() {
        typeRequest = TypeRequest.Empty
        composeTestRule.waitUntilDoesNotExist(hasTestTag(SPLASH_CONTAINER), timeoutMillis = 1100)
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("motorola")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(LOADING_CONTAINER))
        composeTestRule.waitUntilExists(hasTestTag(HomeTestTags.EMPTY_SCREEN))
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertDoesNotExist()
    }

    @Test
    fun assert_connectivity_error_screen_is_displayed() {
        typeRequest = TypeRequest.Connectivity
        composeTestRule.waitUntilDoesNotExist(hasTestTag(SPLASH_CONTAINER), timeoutMillis = 1100)
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("m")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(LOADING_CONTAINER))
        composeTestRule.waitUntilExists(hasTestTag(ERROR_CONTAINER))
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertDoesNotExist()
        composeTestRule.onNodeWithTag(ERROR_MESSAGE)
            .assertTextContains(composeTestRule.activity.getString(RD.string.connectivity_error))
    }

    @Test
    fun assert_can_navigate_to_detail() {
        typeRequest = TypeRequest.NoError
        composeTestRule.waitUntilDoesNotExist(hasTestTag(SPLASH_CONTAINER), timeoutMillis = 1100)
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("motorola")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.waitUntilExists(hasTestTag(LOADING_CONTAINER), timeoutMillis = 2500)
        composeTestRule.waitUntilExists(hasTestTag(CONTAINER_PRODUCTS), timeoutMillis = 2500)
        composeTestRule.onAllNodesWithTag(ITEM_CONTAINER)[0].performClick()
        composeTestRule.onNodeWithTag(DetailTestTags.CONTAINER).assertExists()
        composeTestRule.onNodeWithTag(DetailTestTags.CONTAINER).assertIsDisplayed()
    }
}

private sealed class TypeRequest {
    object Idle : TypeRequest()
    object Connectivity : TypeRequest()
    object ServerError : TypeRequest()
    object NoError : TypeRequest()
    object Empty : TypeRequest()
}

private var typeRequest: TypeRequest = TypeRequest.NoError

private val searchDispatcher by lazy {
    object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (typeRequest) {
                TypeRequest.Empty -> mockResponse(
                    fileName = FILE_SUCCESS_EMPTY_RESPONSE,
                    responseCode = HttpURLConnection.HTTP_OK,
                    delay = 500L
                )
                TypeRequest.Connectivity -> MockResponse().apply {
                    setBodyDelay(500, TimeUnit.MILLISECONDS)
                    setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
                }
                TypeRequest.NoError -> mockResponse(
                    fileName = FILE_SUCCESS_SEARCH_RESPONSE,
                    responseCode = HttpURLConnection.HTTP_OK,
                    delay = 800
                )
                TypeRequest.ServerError -> {
                    MockResponse().apply {
                        setBodyDelay(500, TimeUnit.MILLISECONDS)
                        setSocketPolicy(SocketPolicy.DISCONNECT_DURING_RESPONSE_BODY)
                        setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                        setBody("")
                    }
                }
                TypeRequest.Idle -> mockResponse(
                    fileName = FILE_SUCCESS_SEARCH_RESPONSE,
                    responseCode = HttpURLConnection.HTTP_OK,
                    delay = 500L
                )
            }
        }

    }
}