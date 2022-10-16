package com.bikcodeh.melichallenge.presentation.ui.screens.home

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
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
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

    private fun setView() {
        composeTestRule.activity.setContent {
            MeliNavigation(rememberNavController())
        }
    }

    @Test
    fun assert_server_error_screen_is_displayed() {
        typeRequest = TypeRequest.ServerError
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("motorola")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertIsDisplayed()
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag(ERROR_CONTAINER)
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertDoesNotExist()
        composeTestRule.onNodeWithTag(ERROR_MESSAGE)
            .assertTextContains(composeTestRule.activity.getString(RD.string.internal_server_error))
    }

    @Test
    fun assert_initial_state_is_displayed() {
        composeTestRule.onNodeWithTag(HomeTestTags.INITIAL_SCREEN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(HomeTestTags.MESSAGE_GENERIC_SCREEN)
            .assertTextContains(composeTestRule.activity.getString(R.string.search_description))
    }

    @Test
    fun assert_search_bar_initial_state_is_displayed() {
        composeTestRule.onNodeWithTag(SearchTestTags.CONTAINER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT)
            .assertTextContains(composeTestRule.activity.getString(R.string.search_in_meli))
    }

    @Test
    fun assert_products_are_displayed() {
        typeRequest = TypeRequest.NoError
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("motorola")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertIsDisplayed()
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag(HomeTestTags.CONTAINER_PRODUCTS)
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertDoesNotExist()
    }

    @Test
    fun assert_empty_products_screen_is_displayed() {
        typeRequest = TypeRequest.Empty
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("motorola")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertIsDisplayed()
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag(HomeTestTags.EMPTY_SCREEN)
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertDoesNotExist()
    }

    @Test
    fun assert_connectivity_error_screen_is_displayed() {
        typeRequest = TypeRequest.Connectivity
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performTextInput("motorola")
        composeTestRule.onNodeWithTag(SearchTestTags.INPUT).performImeAction()
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertIsDisplayed()
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag(ERROR_CONTAINER)
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithTag(LOADING_CONTAINER).assertDoesNotExist()
        composeTestRule.onNodeWithTag(ERROR_MESSAGE)
            .assertTextContains(composeTestRule.activity.getString(RD.string.connectivity_error))
    }
}

private sealed class TypeRequest {
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
                    setBodyDelay(1000, TimeUnit.MILLISECONDS)
                    setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
                }
                TypeRequest.NoError -> mockResponse(
                    fileName = FILE_SUCCESS_SEARCH_RESPONSE,
                    responseCode = HttpURLConnection.HTTP_OK,
                    delay = 500L
                )
                TypeRequest.ServerError -> {
                    MockResponse().apply {
                        setBodyDelay(1000, TimeUnit.MILLISECONDS)
                        setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                    }
                }
            }
        }

    }
}