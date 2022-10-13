package com.bikcodeh.melichallenge.data.util

import com.bikcodeh.melichallenge.domain.model.Product
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilTest {

    private val product = Product(
        id = "1",
        title = "test",
        price = 2.0,
        availableQuantity = 2,
        soldQuantity = 5,
        condition = "new",
        thumbnail = "image",
        quantity = 1
    )

    private val productJson = """
            {"id":"1","title":"test","price":2.0,"availableQuantity":2,"soldQuantity":5,"condition":"new","thumbnail":"image","quantity":1}
        """.trimIndent()
    @Test
    fun `toJson should parse object`() {
        val result = Util.toJson(product)
        assertThat(result).isInstanceOf(String::class.java)
        assertThat(result).isEqualTo(productJson)
    }

    @Test
    fun `fromJson should parse string`() {
        val result = Util.fromJson<Product>(productJson)
        assertThat(result).isNotNull()
        assertThat(result).isInstanceOf(Product::class.java)
        result?.run {
            assertThat(id).isEqualTo("1")
            assertThat(title).isEqualTo("test")
            assertThat(price).isEqualTo(2.0)
            assertThat(availableQuantity).isEqualTo(2)
            assertThat(soldQuantity).isEqualTo(5)
            assertThat(condition).isEqualTo("new")
            assertThat(thumbnail).isEqualTo("image")
            assertThat(quantity).isEqualTo(1)
        }
    }

    @Test
    fun `fromJson should return null without a valid json`() {
        val result = Util.fromJson<Product>("""{ "data": "test" }""")
        assertThat(result).isNull()
    }

    @Test
    fun `fromJson should return null without a json`() {
        val result = Util.fromJson<Product>("")
        assertThat(result).isNull()
    }
}