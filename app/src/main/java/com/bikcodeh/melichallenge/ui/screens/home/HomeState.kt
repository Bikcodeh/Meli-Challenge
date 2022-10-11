package com.bikcodeh.melichallenge.ui.screens.home

import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.ui.util.BaseViewModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val products: List<Product>? = null,
    val error: BaseViewModel.Error? = null,
)