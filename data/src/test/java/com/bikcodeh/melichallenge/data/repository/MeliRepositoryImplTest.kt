package com.bikcodeh.melichallenge.data.repository

import com.bikcodeh.melichallenge.data.remote.response.ProductDTO
import com.bikcodeh.melichallenge.data.remote.response.ProductDescriptionResponse
import com.bikcodeh.melichallenge.data.remote.response.SearchResponse
import com.bikcodeh.melichallenge.data.remote.service.MeliService
import com.bikcodeh.melichallenge.domain.repository.MeliRepository
import com.bikcodeh.melichallenge.util.CoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import com.bikcodeh.melichallenge.domain.common.Result
import io.mockk.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class MeliRepositoryImplTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var meliService: MeliService

    private lateinit var meliRepository: MeliRepository

    private val slot = slot<String>()

    @Before
    fun setUp() {
        meliRepository = MeliRepositoryImpl(meliService)
    }

    @Test
    fun `getSearchedArticles should return a success result`() = runTest {
        /** Given */
        val searchResponse = SearchResponse(
            results = listOf(
                ProductDTO(
                    id = "",
                    title = "test",
                    price = 3.0,
                    availableQuantity = 1,
                    soldQuantity = 9,
                    condition = "new",
                    thumbnail = ""
                )
            )
        )
        val response = mockk<Response<SearchResponse>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns searchResponse
        coEvery { meliService.searchProducts(capture(slot)) } answers { response }

        /** When */
        val result = meliRepository.searchProducts("motorola")

        /** Then */
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.count()).isEqualTo(1)
        assertThat((result).data.first().title).isEqualTo("test")
        assertThat((result).data.first().availableQuantity).isEqualTo(1)
    }
    @Test
    fun `getSearchedArticles should return a exception result)`() = runTest {
        /** Given */
        val exception = UnknownHostException("error")
        coEvery { meliService.searchProducts(capture(slot)) } throws exception

        /** When */
        val result = meliRepository.searchProducts("motorola")

        /** Then */
        assertThat(result).isInstanceOf(Result.Exception::class.java)
        assertThat((result as Result.Exception).exception.message).isEqualTo("error")
        coVerify { meliService.searchProducts("motorola") }
        assertThat(slot.captured).isEqualTo("motorola")
    }

    @Test
    fun `getSearchedArticles should return a error result)`() = runTest {
        /** Given */
        val response = mockk<Response<SearchResponse>>()
        every { response.isSuccessful } returns false
        every { response.body() } returns null
        every { response.code() } returns 404
        every { response.message() } returns "error"

        coEvery { meliService.searchProducts(capture(slot)) } returns response

        /** When */
        val result = meliRepository.searchProducts("motorola")

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).code).isEqualTo(404)
        assertThat((result).message).isEqualTo("error")
        coVerify { meliService.searchProducts("motorola") }
        verify { response.isSuccessful }
        verify { response.body() }
        verify { response.code() }
        verify { response.message() }
        assertThat(slot.captured).isEqualTo("motorola")
    }

    @Test
    fun `getProductDescription should return a success description`() = runTest {
        val productDescriptionResponse = ProductDescriptionResponse(description = "test")
        val response = mockk<Response<ProductDescriptionResponse>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns productDescriptionResponse
        coEvery { meliService.getProductDescription("1") } returns response

        val result = meliRepository.getProductDescription("1")

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.description).isEqualTo("test")

        verifyAll {
            response.isSuccessful
            response.body()
        }
        coVerify { meliService.getProductDescription("1") }
    }

    @Test
    fun `getProductDescription should return a success empty description`() = runTest {
        val productDescriptionResponse = ProductDescriptionResponse(description = "")
        val response = mockk<Response<ProductDescriptionResponse>>()
        every { response.isSuccessful } returns true
        every { response.body() } returns productDescriptionResponse
        coEvery { meliService.getProductDescription("1") } returns response

        val result = meliRepository.getProductDescription("1")

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data.description).isEqualTo("")

        verifyAll {
            response.isSuccessful
            response.body()
        }
        coVerify { meliService.getProductDescription("1") }
    }

    @Test
    fun `getProductDescription should return a exception result)`() = runTest {
        /** Given */
        val exception = UnknownHostException("error")
        coEvery { meliService.getProductDescription(capture(slot)) } throws exception

        /** When */
        val result = meliRepository.getProductDescription("1")

        /** Then */
        assertThat(result).isInstanceOf(Result.Exception::class.java)
        assertThat((result as Result.Exception).exception.message).isEqualTo("error")
        coVerify { meliService.getProductDescription("1") }
        assertThat(slot.captured).isEqualTo("1")
    }

    @Test
    fun `getProductDescription should return a error result)`() = runTest {
        /** Given */
        val response = mockk<Response<ProductDescriptionResponse>>()
        every { response.isSuccessful } returns false
        every { response.body() } returns null
        every { response.code() } returns 404
        every { response.message() } returns "error"

        coEvery { meliService.getProductDescription(capture(slot)) } returns response

        /** When */
        val result = meliRepository.getProductDescription("1")

        /** Then */
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).code).isEqualTo(404)
        assertThat((result).message).isEqualTo("error")
        coVerify { meliService.getProductDescription("1") }
        verify { response.isSuccessful }
        verify { response.body() }
        verify { response.code() }
        verify { response.message() }
        assertThat(slot.captured).isEqualTo("1")
    }
}