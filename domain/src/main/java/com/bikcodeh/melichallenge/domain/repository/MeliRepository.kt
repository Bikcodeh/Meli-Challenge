package com.bikcodeh.melichallenge.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.bikcodeh.melichallenge.domain.model.Product
import com.bikcodeh.melichallenge.domain.model.ProductDescription
import com.bikcodeh.melichallenge.domain.common.Result
import kotlinx.coroutines.flow.Flow

interface MeliRepository {
    fun searchProducts(query: String): Flow<PagingData<Product>>
    suspend fun getProductDescription(productId: String): Result<ProductDescription>
}