package com.bikcodeh.melichallenge.presentation.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.bikcodeh.melichallenge.domain.common.fold
import com.bikcodeh.melichallenge.domain.common.toError
import com.bikcodeh.melichallenge.domain.common.validateHttpErrorCode
import com.bikcodeh.melichallenge.domain.di.IoDispatcher
import com.bikcodeh.melichallenge.domain.usecase.SearchProductsUseCase
import com.bikcodeh.melichallenge.presentation.ui.screens.home.HomeUiState
import com.bikcodeh.melichallenge.presentation.ui.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> get() = _homeUiState.asStateFlow()

    val searchQuery: MutableState<String> = mutableStateOf("")

    fun searchProducts(query: String) {
        _homeUiState.update { currentState ->
            currentState.copy(
                isLoading = true,
                products = null,
                error = null,
                initialState = false
            )
        }
        viewModelScope.launch(dispatcher) {
            searchProductsUseCase(query)
                .fold(
                    onSuccess = {
                        _homeUiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                products = it,
                                error = null,
                                initialState = false
                            )
                        }
                    },
                    onError = { errorCode, _ ->
                        val error = handleError(errorCode.validateHttpErrorCode())
                        _homeUiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = error,
                                products = null,
                                initialState = false

                            )
                        }
                    },
                    onException = { exception ->
                        val error = handleError(exception.toError())
                        _homeUiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = error,
                                products = null,
                                initialState = false
                            )
                        }
                    }
                )
        }
    }

    fun onUpdateQuery(query: String) {
        searchQuery.value = query
    }

    fun clearQuery() {
        searchQuery.value = String()
    }
}