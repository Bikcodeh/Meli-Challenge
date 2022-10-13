package com.bikcodeh.melichallenge.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilTest {

    @Test
    fun currencyFormatter() {
        val result = Util.currencyFormatter(1000)
        assertThat(result).isEqualTo("1,000")
    }

    @Test
    fun `currencyFormatter with value 0`() {
        val result = Util.currencyFormatter(0)
        assertThat(result).isEqualTo("0")
    }
}