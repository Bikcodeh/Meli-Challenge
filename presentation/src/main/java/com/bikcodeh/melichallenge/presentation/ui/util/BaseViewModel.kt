package com.bikcodeh.melichallenge.presentation.ui.util

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.bikcodeh.melichallenge.domain.R as RD
import com.bikcodeh.melichallenge.domain.common.Error as ErrorDomain

/**
 * Class to avoid repeat code using handleError function in others view models
 */
open class BaseViewModel : ViewModel() {

    fun handleError(error: ErrorDomain): Error {
        return when (error) {
            ErrorDomain.Connectivity -> Error(
                errorMessage = RD.string.connectivity_error,
                displayTryAgainBtn = true
            )
            is ErrorDomain.HttpException -> Error(
                errorMessage = error.messageResId,
                displayTryAgainBtn = true
            )
            is ErrorDomain.Unknown -> Error(
                errorMessage = RD.string.unknown_error,
                displayTryAgainBtn = false
            )
            ErrorDomain.InternetConnection -> Error(
                errorMessage = RD.string.internet_error,
                displayTryAgainBtn = true
            )
        }
    }

    data class Error(
        val displayTryAgainBtn: Boolean = false,
        @StringRes val errorMessage: Int? = null
    )
}