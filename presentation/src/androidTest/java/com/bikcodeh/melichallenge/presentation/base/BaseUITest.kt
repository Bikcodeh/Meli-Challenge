package com.bikcodeh.melichallenge.presentation.base

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After

open class BaseUITest(private val dispatcher: Dispatcher) {

    private lateinit var mockServer: MockWebServer

    init {
        startMockServer()
    }

    private fun startMockServer() {
        mockServer = MockWebServer()
        mockServer.dispatcher = dispatcher
        mockServer.start(MOCK_WEB_SERVER_PORT)
    }

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    protected fun enqueueResponses(vararg response: MockResponse) {
        response.forEach { mockServer.enqueue(it) }
    }

    @After
    open fun tearDown() {
        stopMockServer()
    }
}

private const val MOCK_WEB_SERVER_PORT = 8080