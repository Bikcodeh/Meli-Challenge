package com.bikcodeh.melichallenge.domain.usecase

import com.bikcodeh.melichallenge.core_test.util.CoroutineRule
import com.bikcodeh.melichallenge.domain.common.Result
import com.bikcodeh.melichallenge.domain.model.ProductDescription
import com.bikcodeh.melichallenge.domain.repository.MeliRepository
import com.google.common.truth.Truth.assertThat
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
class GetProductDescriptionUseCaseTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var meliRepository: MeliRepository

    private lateinit var getProductDescriptionUseCase: GetProductDescriptionUseCase

    private val slot = slot<String>()

    @Before
    fun setUp() {
        getProductDescriptionUseCase = GetProductDescriptionUseCase(meliRepository)
    }

    @Test
    fun search_products_should_return_a_sucessfull_description() = runTest {
        /** Given */
        coEvery { meliRepository.getProductDescription(capture(slot)) } returns Result.Success(
            ProductDescription("test")
        )
        /** When */
        val result = getProductDescriptionUseCase("1")

        /** Then */
        assertThat(slot.captured).isEqualTo("1")
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(result).isNotNull()
        assertThat((result as Result.Success).data.description).isEqualTo("test")
        coVerify { meliRepository.getProductDescription("1") }
    }

    @Test
    fun search_products_should_return_a_sucessfull_empty_description() = runTest {
        /** Given */
        coEvery { meliRepository.getProductDescription(capture(slot)) } returns Result.Success(
            ProductDescription("")
        )
        /** When */
        val result = getProductDescriptionUseCase("1")

        /** Then */
        assertThat(slot.captured).isEqualTo("1")
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(result).isNotNull()
        assertThat((result as Result.Success).data.description).isEqualTo("")
        coVerify { meliRepository.getProductDescription("1") }
    }

    @Test
    fun search_products_should_return_a_error_result() = runTest {
        /** Given */
        coEvery { meliRepository.getProductDescription(capture(slot)) } returns Result.Error(
            401, "error"
        )
        /** When */
        val result = getProductDescriptionUseCase("1")

        /** Then */
        assertThat(slot.captured).isEqualTo("1")
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result).isNotNull()
        assertThat((result as Result.Error).code).isEqualTo(401)
        assertThat((result).message).isEqualTo("error")
        coVerify { meliRepository.getProductDescription("1") }
    }

    @Test
    fun search_products_should_return_a_exception_result() = runTest {
        /** Given */
        coEvery { meliRepository.getProductDescription(capture(slot)) } returns Result.Exception(
            UnknownHostException("error")
        )
        /** When */
        val result = getProductDescriptionUseCase("1")

        /** Then */
        assertThat(slot.captured).isEqualTo("1")
        assertThat(result).isInstanceOf(Result.Exception::class.java)
        assertThat(result).isNotNull()
        assertThat((result as Result.Exception).exception).isInstanceOf(UnknownHostException::class.java)
        assertThat((result).exception.message).isEqualTo("error")
        coVerify { meliRepository.getProductDescription("1") }
    }
}