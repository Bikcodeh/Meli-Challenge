package com.bikcodeh.melichallenge.ui.screens.detail

import com.bikcodeh.melichallenge.core_test.util.CoroutineRule
import com.bikcodeh.melichallenge.domain.usecase.GetProductDescriptionUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var getProductDescriptionUseCase: GetProductDescriptionUseCase

    private lateinit var detailViewModel: DetailViewModel

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
    fun getProductDescription() {
    }
}