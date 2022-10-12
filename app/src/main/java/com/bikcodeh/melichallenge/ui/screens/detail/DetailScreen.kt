package com.bikcodeh.melichallenge.ui.screens.detail

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bikcodeh.melichallenge.domain.model.Product

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun DetailScreen(
    product: Product,
    detailViewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val productDescriptionState by detailViewModel.productDescription.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = product) {
        detailViewModel.getProductDescription(product.id)
    }

    DetailContent(
        product = product,
        onBack = onBack,
        productDescriptionState = productDescriptionState
    )
}