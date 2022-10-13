package com.bikcodeh.melichallenge.ui.screens.detail

import androidx.lifecycle.viewModelScope
import com.bikcodeh.melichallenge.domain.common.fold
import com.bikcodeh.melichallenge.domain.di.IoDispatcher
import com.bikcodeh.melichallenge.domain.usecase.GetProductDescriptionUseCase
import com.bikcodeh.melichallenge.ui.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductDescriptionUseCase: GetProductDescriptionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel() {
    private val _productDescriptionState: MutableStateFlow<ProductDescriptionUiState> =
        MutableStateFlow(ProductDescriptionUiState())
    val productDescriptionState: StateFlow<ProductDescriptionUiState>
        get() = _productDescriptionState
            .asStateFlow()

    fun getProductDescription(productId: String) {
        _productDescriptionState
            .update { state -> state.copy(isLoading = true, hasError = false) }
        viewModelScope.launch(dispatcher) {
            getProductDescriptionUseCase(productId)
                .fold(
                    onSuccess = {
                        _productDescriptionState
                            .update { state ->
                                state.copy(
                                    isLoading = false,
                                    description = it.description,
                                    hasError = false
                                )
                            }
                    },
                    onError = { _, _ ->
                        _productDescriptionState
                            .update { state ->
                                state.copy(
                                    isLoading = false,
                                    hasError = true,
                                    description = ""
                                )
                            }
                    }, onException = {
                        _productDescriptionState
                            .update { state ->
                                state.copy(
                                    isLoading = false,
                                    hasError = true,
                                    description = ""
                                )
                            }
                    }
                )
        }
    }
}