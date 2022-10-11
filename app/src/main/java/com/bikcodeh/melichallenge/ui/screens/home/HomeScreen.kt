package com.bikcodeh.melichallenge.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.bikcodeh.melichallenge.ui.util.extension.collectAsStateWithLifecycle
import com.bikcodeh.melichallenge.ui.util.extension.rememberStateWithLifecycle

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val state by rememberStateWithLifecycle(stateFlow = homeViewModel.homeUiState)
    val query by homeViewModel.searchQuery

    HomeContent(
        text = query,
        onTextChange = { homeViewModel.onUpdateQuery(it) },
        onClearSearch = { homeViewModel.clearQuery() },
        onCloseSearch = { },
        onSearch = { homeViewModel.searchProducts(it) },
        onProductClick = {},
        homeUiState = state
    )
}