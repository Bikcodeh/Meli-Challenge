package com.bikcodeh.melichallenge.presentation.ui.util

import org.junit.Before
import org.junit.Test
import com.bikcodeh.melichallenge.domain.common.Error
import com.bikcodeh.melichallenge.presentation.ui.util.BaseViewModel
import com.google.common.truth.Truth.assertThat
import com.bikcodeh.melichallenge.domain.R as RD

class BaseViewModelTest {

    private lateinit var baseViewModel: BaseViewModel

    @Before
    fun setUp() {
        baseViewModel = BaseViewModel()
    }

    @Test
    fun `handleError should handle a connectivity error`() {
        val result = baseViewModel.handleError(Error.Connectivity)
        assertThat(result.displayTryAgainBtn).isTrue()
        assertThat(result.errorMessage).isNotNull()
        assertThat(result.errorMessage).isEqualTo(RD.string.connectivity_error)
    }

    @Test
    fun `handleError should handle a http exception error`() {
        val result = baseViewModel.handleError(Error.HttpException(RD.string.internal_server_error))
        assertThat(result.displayTryAgainBtn).isTrue()
        assertThat(result.errorMessage).isNotNull()
        assertThat(result.errorMessage).isEqualTo(RD.string.internal_server_error)
    }

    @Test
    fun `handleError should handle a unknown error`() {
        val result = baseViewModel.handleError(Error.Unknown("error"))
        assertThat(result.displayTryAgainBtn).isFalse()
        assertThat(result.errorMessage).isNotNull()
        assertThat(result.errorMessage).isEqualTo(RD.string.unknown_error)
    }

    @Test
    fun `handleError should handle an internet connection error`() {
        val result = baseViewModel.handleError(Error.InternetConnection)
        assertThat(result.displayTryAgainBtn).isTrue()
        assertThat(result.errorMessage).isNotNull()
        assertThat(result.errorMessage).isEqualTo(RD.string.internet_error)
    }

    @Test
    fun `error class with default values`() {
        val result = BaseViewModel.Error()
        assertThat(result.displayTryAgainBtn).isFalse()
        assertThat(result.errorMessage).isNull()
    }
}