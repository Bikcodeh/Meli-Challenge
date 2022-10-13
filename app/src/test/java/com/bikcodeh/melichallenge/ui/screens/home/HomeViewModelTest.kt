package com.bikcodeh.melichallenge.ui.screens.home

import com.bikcodeh.melichallenge.core_test.util.CoroutineRule
import com.bikcodeh.melichallenge.domain.usecase.SearchProductsUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
    fun searchProducts() {
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