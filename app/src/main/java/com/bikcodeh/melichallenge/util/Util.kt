package com.bikcodeh.melichallenge.util

import java.text.DecimalFormat

object Util {
    fun currencyFormatter(value: Int): String {
        val formatter = DecimalFormat("###,###,##0")
        return formatter.format(value)
    }
}