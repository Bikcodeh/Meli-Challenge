package com.bikcodeh.melichallenge.data.repository

import com.bikcodeh.melichallenge.data.remote.service.MeliService
import com.bikcodeh.melichallenge.domain.common.Result
import com.bikcodeh.melichallenge.domain.common.fold
import com.bikcodeh.melichallenge.domain.common.makeSafeRequest
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.domain.model.ProductDescription
import com.bikcodeh.melichallenge.domain.repository.MeliRepository
import javax.inject.Inject

/**
 * Class to handle requests to get products and product description
 * @param meliService: service that allow us to get the information from a remote service
 */
class MeliRepositoryImpl @Inject constructor(
    private val meliService: MeliService
) : MeliRepository {

    /**
     * Get a list of products fetching by a query string
     * @param query: word or phrase to fetch products
     * @return Result<List<Product>>: returns a Result wrapper to handle the products or some error
     */
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

    /**
     * Get a product description fetching by a product id
     * @param productId: product id from a specific product to fetch it's description
     * @return Result<List<Product>>: returns a Result wrapper to handle the products or some error
     */
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