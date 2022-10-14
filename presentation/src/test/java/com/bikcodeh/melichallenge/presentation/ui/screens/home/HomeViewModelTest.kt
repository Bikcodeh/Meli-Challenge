package com.bikcodeh.melichallenge.presentation.ui.screens.home

import com.bikcodeh.melichallenge.core_test.util.CoroutineRule
import com.bikcodeh.melichallenge.domain.R
import com.bikcodeh.melichallenge.domain.common.Result
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.domain.usecase.SearchProductsUseCase
import com.bikcodeh.melichallenge.presentation.ui.util.BaseViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
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

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var searchProductsUseCase: SearchProductsUseCase

    private lateinit var homeViewModel: HomeViewModel

    private val slot = slot<String>()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(
            searchProductsUseCase,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun getHomeUiState() {
        assertThat(homeViewModel.homeUiState.value).isInstanceOf(HomeUiState::class.java)
        assertThat(homeViewModel.homeUiState.value).isEqualTo(HomeUiState())
        assertThat(homeViewModel.homeUiState.value.initialState).isTrue()
        assertThat(homeViewModel.homeUiState.value.isLoading).isFalse()
        assertThat(homeViewModel.homeUiState.value.error).isNull()
        assertThat(homeViewModel.homeUiState.value.products).isNull()
    }

    @Test
    fun getSearchQuery() {
        assertThat(homeViewModel.searchQuery.value).isEqualTo("")
    }

    @Test
    fun `searchProducts should return a successful list of products`() = runTest {
        /** Given */
        coEvery { searchProductsUseCase(capture(slot)) } returns Result.Success(
            listOf(
                Product(
                    id = "1",
                    title = "test",
                    price = 2.0,
                    availableQuantity = 7,
                    soldQuantity = 2,
                    condition = "new",
                    thumbnail = "image",
                    quantity = 1
                )
            )
        )
        val results = arrayListOf<HomeUiState>()
        val job = launch(UnconfinedTestDispatcher()) {
            homeViewModel.homeUiState.toList(results)
        }
        /** When */
        homeViewModel.searchProducts("car")

        /** Then */
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].products).isNull()
        assertThat(results[1].error).isNull()
        assertThat(results[1].initialState).isFalse()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].products).isNotNull()
        assertThat(results[2].products?.count()).isEqualTo(1)
        assertThat(results[2].products?.first()?.id).isEqualTo("1")
        assertThat(results[2].products?.first()?.title).isEqualTo("test")
        assertThat(results[2].products?.first()?.price).isEqualTo(2.0)
        assertThat(results[2].products?.first()?.availableQuantity).isEqualTo(7)
        assertThat(results[2].products?.first()?.soldQuantity).isEqualTo(2)
        assertThat(results[2].products?.first()?.condition).isEqualTo("new")
        assertThat(results[2].products?.first()?.thumbnail).isEqualTo("image")
        assertThat(results[2].products?.first()?.quantity).isEqualTo(1)
        assertThat(results[2].error).isNull()
        assertThat(results[2].initialState).isFalse()
        assertThat(slot.captured).isEqualTo("car")
        coVerify { searchProductsUseCase("car") }
        job.cancel()
    }

    @Test
    fun `searchProducts should return a successful empty list of products`() = runTest {
        /** Given */
        coEvery { searchProductsUseCase(capture(slot)) } returns Result.Success(
            emptyList()
        )
        val results = arrayListOf<HomeUiState>()
        val job = launch(UnconfinedTestDispatcher()) {
            homeViewModel.homeUiState.toList(results)
        }
        /** When */
        homeViewModel.searchProducts("car")

        /** Then */
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].products).isNull()
        assertThat(results[1].error).isNull()
        assertThat(results[1].initialState).isFalse()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].products).isNotNull()
        assertThat(results[2].products?.count()).isEqualTo(0)
        assertThat(results[2].error).isNull()
        assertThat(results[2].initialState).isFalse()
        assertThat(slot.captured).isEqualTo("car")
        coVerify { searchProductsUseCase("car") }
        job.cancel()
    }

    @Test
    fun `searchProducts should handle a error result (401)`() = runTest {
        /** Given */
        coEvery { searchProductsUseCase(capture(slot)) } returns Result.Error(
            401, "error"
        )
        val results = arrayListOf<HomeUiState>()
        val job = launch(UnconfinedTestDispatcher()) {
            homeViewModel.homeUiState.toList(results)
        }
        /** When */
        homeViewModel.searchProducts("car")

        /** Then */
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].products).isNull()
        assertThat(results[1].error).isNull()
        assertThat(results[1].initialState).isFalse()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].products).isNull()
        assertThat(results[2].error).isNotNull()
        assertThat(results[2].error).isInstanceOf(BaseViewModel.Error::class.java)
        assertThat(results[2].error?.errorMessage).isEqualTo(R.string.unauthorized_error)
        assertThat(results[2].error?.displayTryAgainBtn).isTrue()
        assertThat(results[2].initialState).isFalse()
        assertThat(slot.captured).isEqualTo("car")
        coVerify { searchProductsUseCase("car") }
        job.cancel()
    }

    @Test
    fun `searchProducts should handle a exception result`() = runTest {
        /** Given */
        coEvery { searchProductsUseCase(capture(slot)) } returns Result.Exception(
            Exception()
        )
        val results = arrayListOf<HomeUiState>()
        val job = launch(UnconfinedTestDispatcher()) {
            homeViewModel.homeUiState.toList(results)
        }
        /** When */
        homeViewModel.searchProducts("car")

        /** Then */
        assertThat(results[1].isLoading).isTrue()
        assertThat(results[1].products).isNull()
        assertThat(results[1].error).isNull()
        assertThat(results[1].initialState).isFalse()
        assertThat(results[2].isLoading).isFalse()
        assertThat(results[2].products).isNull()
        assertThat(results[2].error).isNotNull()
        assertThat(results[2].error).isInstanceOf(BaseViewModel.Error::class.java)
        assertThat(results[2].error?.errorMessage).isEqualTo(R.string.unknown_error)
        assertThat(results[2].error?.displayTryAgainBtn).isFalse()
        assertThat(results[2].initialState).isFalse()
        assertThat(slot.captured).isEqualTo("car")
        coVerify { searchProductsUseCase("car") }
        job.cancel()
    }

    @Test
    fun onUpdateQuery() {
        homeViewModel.onUpdateQuery("test")
        assertThat(homeViewModel.searchQuery.value).isEqualTo("test")
    }

    @Test
    fun clearQuery() {
        homeViewModel.onUpdateQuery("test")
        assertThat(homeViewModel.searchQuery.value).isEqualTo("test")
        homeViewModel.clearQuery()
        assertThat(homeViewModel.searchQuery.value).isEqualTo("")
    }
}