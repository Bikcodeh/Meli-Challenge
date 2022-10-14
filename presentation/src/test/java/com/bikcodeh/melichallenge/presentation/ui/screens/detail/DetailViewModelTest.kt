package com.bikcodeh.melichallenge.presentation.ui.screens.detail

import com.bikcodeh.melichallenge.core_test.util.CoroutineRule
import com.bikcodeh.melichallenge.domain.usecase.GetProductDescriptionUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.bikcodeh.melichallenge.domain.common.Result
import com.bikcodeh.melichallenge.domain.model.ProductDescription
import io.mockk.coVerify

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var getProductDescriptionUseCase: GetProductDescriptionUseCase

    private lateinit var detailViewModel: DetailViewModel

    private val slot = slot<String>()

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(getProductDescriptionUseCase, UnconfinedTestDispatcher())
    }

    @Test
    fun getProductDescriptionState() {
        assertThat(detailViewModel.productDescriptionState.value).isInstanceOf(
            ProductDescriptionUiState::class.java
        )
        assertThat(detailViewModel.productDescriptionState.value).isEqualTo(
            ProductDescriptionUiState()
        )
        assertThat(detailViewModel.productDescriptionState.value.description).isEqualTo(String())
        assertThat(detailViewModel.productDescriptionState.value.hasError).isFalse()
        assertThat(detailViewModel.productDescriptionState.value.isLoading).isFalse()
    }

    @Test
    fun `getProductDescription should be successful`() = runTest {
        /** Given */
        coEvery { getProductDescriptionUseCase(capture(slot)) } returns Result.Success(
            ProductDescription("test")
        )
        val results = arrayListOf<ProductDescriptionUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            detailViewModel.productDescriptionState.toList(results)
        }

        /** When */
        detailViewModel.getProductDescription("1")

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].hasError).isFalse()
        assertThat(results[2].description).isEqualTo("test")
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].hasError).isFalse()

        coVerify(exactly = 1) { getProductDescriptionUseCase("1") }
        job.cancel()
    }

    @Test
    fun `getProductDescription should be an error`() = runTest {
        /** Given */
        coEvery { getProductDescriptionUseCase(capture(slot)) } returns Result.Error(
            401, "error"
        )
        val results = arrayListOf<ProductDescriptionUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            detailViewModel.productDescriptionState.toList(results)
        }

        /** When */
        detailViewModel.getProductDescription("1")

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].hasError).isFalse()
        assertThat(results[2].description).isEqualTo("")
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].hasError).isTrue()

        coVerify(exactly = 1) { getProductDescriptionUseCase("1") }
        job.cancel()
    }

    @Test
    fun `getProductDescription should be an exception`() = runTest {
        /** Given */
        coEvery { getProductDescriptionUseCase(capture(slot)) } returns Result.Exception(
            Exception()
        )
        val results = arrayListOf<ProductDescriptionUiState>()

        val job = launch(UnconfinedTestDispatcher()) {
            detailViewModel.productDescriptionState.toList(results)
        }

        /** When */
        detailViewModel.getProductDescription("1")

        /** Then */
        assertThat(results[0].isLoading).isFalse()
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].hasError).isFalse()
        assertThat(results[2].description).isEqualTo("")
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].hasError).isTrue()

        coVerify(exactly = 1) { getProductDescriptionUseCase("1") }
        job.cancel()
    }
}