package com.bikcodeh.melichallenge.presentation.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.bikcodeh.melichallenge.domain.model.Product

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (product: Product) -> Unit
) {
    val state by homeViewModel.homeUiState.collectAsStateWithLifecycle()
    val query by homeViewModel.searchQuery
    val products = homeViewModel.products.collectAsStateWithLifecycle().value.collectAsLazyPagingItems()

    HomeContent(
        text = query,
        onTextChange = { homeViewModel.onUpdateQuery(it) },
        onClearSearch = { homeViewModel.clearQuery() },
        onCloseSearch = { },
        onSearch = { homeViewModel.searchProducts(it) },
        onProductClick = { navigateToDetail(it) },
        homeUiState = state,
        products = products
    )
}