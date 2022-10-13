package com.bikcodeh.melichallenge.data.remote.response

import com.bikcodeh.melichallenge.domain.model.Product
import com.squareup.moshi.Json

/**
 * Class to get the response of products from service
 */
data class SearchResponse(
    val results: List<ProductDTO>
)

/**
 * Class to map the product item from service to kotlin class
 */
data class ProductDTO(
    val id: String,
    val title: String,
    val price: Double,
    @Json(name = "available_quantity")
    val availableQuantity: Int,
    @Json(name = "sold_quantity")
    val soldQuantity: Int,
    val condition: String,
    val thumbnail: String
) {
    fun toDomain(): Product {
        return Product(
            id,
            title,
            price,
            availableQuantity,
            soldQuantity,
            condition,
            thumbnail
        )
    }
}