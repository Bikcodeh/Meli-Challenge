package com.bikcodeh.melichallenge.domain.repository

import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.domain.model.ProductDescription
import com.bikcodeh.melichallenge.domain.common.Result

interface MeliRepository {
    suspend fun searchProducts(query: String): Result<List<Product>>
    suspend fun getProductDescription(productId: String): Result<ProductDescription>
}