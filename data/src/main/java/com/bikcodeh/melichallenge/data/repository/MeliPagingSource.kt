package com.bikcodeh.melichallenge.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bikcodeh.melichallenge.data.remote.service.MeliService
import com.bikcodeh.melichallenge.domain.common.getException
import com.bikcodeh.melichallenge.domain.common.getHttpErrorCode
import com.bikcodeh.melichallenge.domain.common.getSuccess
import com.bikcodeh.melichallenge.domain.common.makeSafeRequest
import com.bikcodeh.melichallenge.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

private const val PRODUCTS_STARTING_OFFSET_INDEX = 0

class MeliPagingSource @Inject constructor(
    private val meliService: MeliService,
    private val query: String
) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition = anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition = anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val offset = params.key ?: PRODUCTS_STARTING_OFFSET_INDEX
        return try {
            val response = withContext(Dispatchers.IO) {
                makeSafeRequest { meliService.searchProducts(query = query, offset = offset) }
            }
            response.getException()?.let {
                throw it
            }
            response.getHttpErrorCode()?.let {
                throw HttpException(
                    Response.error<ResponseBody>(
                        it,
                        "".toResponseBody("plain/text".toMediaType())
                    )
                )
            }
            val products = response.getSuccess()?.results?.map { it.toDomain() }.orEmpty()

            val prevKey = if (offset > PRODUCTS_STARTING_OFFSET_INDEX) offset - 1 else null
            val nextKey = if (products.isNotEmpty()) {
                offset + 1
            } else {
                null
            }

            LoadResult.Page(
                data = products,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: UnknownHostException) {
            LoadResult.Error(exception)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}