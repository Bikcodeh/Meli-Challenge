package com.bikcodeh.melichallenge.presentation.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bikcodeh.melichallenge.domain.di.IoDispatcher
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.domain.usecase.SearchProductsUseCase
import com.bikcodeh.melichallenge.presentation.ui.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> get() = _homeUiState.asStateFlow()

    val products = MutableStateFlow<Flow<PagingData<Product>>>(flowOf(PagingData.empty<Product>()))

    val searchQuery: MutableState<String> = mutableStateOf("")

    fun searchProducts(query: String) {
        products.value = searchProductsUseCase(query)
            .cachedIn(viewModelScope)
        _homeUiState.update { it.copy(initialState = false) }
    }

    fun onUpdateQuery(query: String) {
        searchQuery.value = query
    }

    fun clearQuery() {
        searchQuery.value = String()
    }
}