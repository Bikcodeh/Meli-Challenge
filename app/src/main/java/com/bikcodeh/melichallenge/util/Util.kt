package com.bikcodeh.melichallenge.util

import java.text.DecimalFormat

object Util {
    /**
     * Format a given number with the specific format
     * @param value: value to be formatted
     * @return String: the number formatted in string
     */
    fun currencyFormatter(value: Int): String {
        val formatter = DecimalFormat("###,###,##0")
        return formatter.format(value)
    }
}