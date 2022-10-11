package com.bikcodeh.melichallenge.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val state by homeViewModel.homeUiState.collectAsStateWithLifecycle()
    val query by homeViewModel.searchQuery

    LaunchedEffect(key1 = Unit) {
        if (state.products == null)
            homeViewModel.searchProducts(query)
    }

    HomeContent(
        text = query,
        onTextChange = { homeViewModel.onUpdateQuery(it) },
        onClearSearch = { homeViewModel.clearQuery() },
        onCloseSearch = { },
        onSearch = { homeViewModel.searchProducts(it) },
        onProductClick = {},
        homeUiState = state,
        onRefresh = { homeViewModel.searchProducts(query) }
    )
}