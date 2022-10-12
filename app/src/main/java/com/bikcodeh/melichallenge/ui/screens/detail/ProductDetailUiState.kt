package com.bikcodeh.melichallenge.ui.screens.detail

data class ProductDescriptionUiState(
    val isLoading: Boolean = false,
    val description: String = String(),
    val hasError: Boolean = false
)