package com.bikcodeh.melichallenge.data.remote.service

import com.bikcodeh.melichallenge.data.remote.response.ProductDescriptionResponse
import com.bikcodeh.melichallenge.data.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliService {

    @GET("search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("limit") limit: String = "10"
    ): Response<SearchResponse>

    @GET("/items/{id}/description")
    suspend fun getProductDescription(@Path("id") itemId: String): Response<ProductDescriptionResponse>
}