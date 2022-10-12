package com.bikcodeh.melichallenge.ui.screens.detail

import androidx.lifecycle.viewModelScope
import com.bikcodeh.melichallenge.domain.common.fold
import com.bikcodeh.melichallenge.domain.common.toError
import com.bikcodeh.melichallenge.domain.common.validateHttpErrorCode
import com.bikcodeh.melichallenge.domain.di.IoDispatcher
import com.bikcodeh.melichallenge.domain.usecase.GetProductDescriptionUseCase
import com.bikcodeh.melichallenge.ui.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductDescriptionUseCase: GetProductDescriptionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): BaseViewModel() {
    private val _productAddChannel = Channel<Boolean>(Channel.CONFLATED)
    val productAddEvents = _productAddChannel.receiveAsFlow()

    private val _productDescription: MutableStateFlow<ProductDescriptionUiState> =
        MutableStateFlow(ProductDescriptionUiState())
    val productDescription: StateFlow<ProductDescriptionUiState> get() = _productDescription.asStateFlow()

    fun getProductDescription(productId: String) {
        _productDescription.update { state -> state.copy(isLoading = true, ) }
        viewModelScope.launch(dispatcher) {
            getProductDescriptionUseCase(productId)
                .fold(
                    onSuccess = {
                        _productDescription.update { state ->
                            state.copy(
                                isLoading = false,
                                description = it.description
                            )
                        }
                    },
                    onError = { code, message ->
                        val error = code.validateHttpErrorCode()
                        _productDescription.update { state -> state.copy(isLoading = false) }
                    }, onException = {
                        val error = it.toError()
                        _productDescription.update { state -> state.copy(isLoading = false) }
                    }
                )
        }
    }
}