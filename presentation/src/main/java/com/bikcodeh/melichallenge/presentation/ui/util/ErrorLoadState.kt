package com.bikcodeh.melichallenge.presentation.ui.util

import com.bikcodeh.melichallenge.domain.common.Error

data class ErrorLoadState(
    var error: Error? = null,
    var isAppend: Boolean = false,
    var isRefresh: Boolean = false
)