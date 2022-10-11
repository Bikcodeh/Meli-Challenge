package com.bikcodeh.melichallenge.data.repository

import com.bikcodeh.melichallenge.data.remote.service.MeliService
import com.bikcodeh.melichallenge.domain.common.Result
import com.bikcodeh.melichallenge.domain.common.fold
import com.bikcodeh.melichallenge.domain.common.makeSafeRequest
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.domain.model.ProductDescription
import com.bikcodeh.melichallenge.domain.repository.MeliRepository
import javax.inject.Inject

class MeliRepositoryImpl @Inject constructor(
    private val meliService: MeliService
) : MeliRepository {

    override suspend fun searchProducts(query: String): Result<List<Product>> {
        val response = makeSafeRequest { meliService.searchProducts(query) }

        return response.fold(
            onSuccess = {
                Result.Success(it.results.map { productDTO -> productDTO.toDomain() })
            },
            onError = { code, message ->
                Result.Error(code, message)
            },
            onException = {
                Result.Exception(it)
            }
        )
    }

    override suspend fun getProductDescription(productId: String): Result<ProductDescription> {
        val response = makeSafeRequest { meliService.getProductDescription(productId) }

        return response.fold(
            onSuccess = {
                Result.Success(it.toDomain())
            },
            onError = { code, message ->
                Result.Error(code, message)
            },
            onException = {
                Result.Exception(it)
            }
        )
    }
}