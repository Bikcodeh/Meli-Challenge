package com.bikcodeh.melichallenge.presentation.network

import androidx.test.platform.app.InstrumentationRegistry
import java.io.BufferedReader
import java.io.Reader
import okhttp3.mockwebserver.MockResponse

fun mockResponse(fileName: String, responseCode: Int): MockResponse =
    MockResponse()
        .setResponseCode(responseCode)
        .setBody(getJson("$MOCK_WEB_SERVER_FOLDER_NAME$fileName"))

private fun getJson(path: String): String {
    var content: String
    val testContext = InstrumentationRegistry.getInstrumentation().context
    val inputStream = testContext.assets.open(path)
    val reader = BufferedReader(inputStream.reader() as Reader)
    reader.use { bufferedReader ->
        content = bufferedReader.readText()
    }
    return content
}

private const val MOCK_WEB_SERVER_FOLDER_NAME = "mocks/"
