package com.bikcodeh.melichallenge.domain.usecase

import com.bikcodeh.melichallenge.core_test.util.CoroutineRule
import com.bikcodeh.melichallenge.domain.common.Result
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.domain.repository.MeliRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class SearchProductsUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    private val slot = slot<String>()

    @RelaxedMockK
    private lateinit var meliRepository: MeliRepository

    private lateinit var searchProductsUseCase: SearchProductsUseCase

    @Before
    fun setUp() {
        searchProductsUseCase = SearchProductsUseCase(meliRepository)
    }

    @Test
    fun search_products_should_return_a_sucessfull_list_of_products() = runTest {
        /** Given */
        coEvery { meliRepository.searchProducts(capture(slot)) } returns Result.Success(
            listOf(
                Product(
                    id = "",
                    title = "test",
                    price = 0.0,
                    availableQuantity = 0,
                    soldQuantity = 0,
                    condition = "",
                    thumbnail = "",
                    quantity = 0
                )
            )
        )
        /** When */
        val result = searchProductsUseCase("motorola")

        /** Then */
        Truth.assertThat(slot.captured).isEqualTo("motorola")
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
        Truth.assertThat(result).isNotNull()
        Truth.assertThat((result as Result.Success).data.count()).isEqualTo(1)
        Truth.assertThat((result).data.first().title).isEqualTo("test")
        coVerify { meliRepository.searchProducts("motorola") }
    }

    @Test
    fun search_products_should_return_a_sucessfull_empty_list_of_products() = runTest {
        /** Given */
        coEvery { meliRepository.searchProducts(capture(slot)) } returns Result.Success(
            emptyList()
        )
        /** When */
        val result = searchProductsUseCase("motorola")

        /** Then */
        Truth.assertThat(slot.captured).isEqualTo("motorola")
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
        Truth.assertThat(result).isNotNull()
        Truth.assertThat((result as Result.Success).data.count()).isEqualTo(0)
        coVerify { meliRepository.searchProducts("motorola") }
    }

    @Test
    fun search_products_should_return_a_error_result() = runTest {
        /** Given */
        coEvery { meliRepository.searchProducts(capture(slot)) } returns Result.Error(
            401, "error"
        )
        /** When */
        val result = searchProductsUseCase("motorola")

        /** Then */
        Truth.assertThat(slot.captured).isEqualTo("motorola")
        Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
        Truth.assertThat(result).isNotNull()
        Truth.assertThat((result as Result.Error).code).isEqualTo(401)
        Truth.assertThat((result).message).isEqualTo("error")
        coVerify { meliRepository.searchProducts("motorola") }
    }

    @Test
    fun search_products_should_return_a_exception_result() = runTest {
        /** Given */
        coEvery { meliRepository.searchProducts(capture(slot)) } returns Result.Exception(
            UnknownHostException("error")
        )
        /** When */
        val result = searchProductsUseCase("motorola")

        /** Then */
        Truth.assertThat(slot.captured).isEqualTo("motorola")
        Truth.assertThat(result).isInstanceOf(Result.Exception::class.java)
        Truth.assertThat(result).isNotNull()
        Truth.assertThat((result as Result.Exception).exception)
            .isInstanceOf(UnknownHostException::class.java)
        Truth.assertThat((result).exception.message).isEqualTo("error")
        coVerify { meliRepository.searchProducts("motorola") }
    }
}