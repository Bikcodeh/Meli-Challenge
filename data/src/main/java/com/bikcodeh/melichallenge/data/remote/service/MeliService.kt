package com.bikcodeh.melichallenge.data.remote.service

import com.bikcodeh.melichallenge.data.remote.response.ProductDescriptionResponse
import com.bikcodeh.melichallenge.data.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliService {

    /**
     * Get a list of products from a remote service
     * @param query: word or phrase to fetch items
     * @param limit: total items wanted per response
     * @return Response<SearchResponse>: returns a response class wrapper with the data SearchResponse
     */
    @GET("search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("limit") limit: String = "10",
        @Query("offset") offset: Int = 0
    ): Response<SearchResponse>

    /**
     * Get a product description from a remote service
     * @param itemId: itemId/productId to find a product description with that specific id
     * @return Response<ProductDescriptionResponse>: returns a response class wrapper with the data ProductDescriptionResponse
     */
    @GET("/items/{id}/description")
    suspend fun getProductDescription(@Path("id") itemId: String): Response<ProductDescriptionResponse>
}