package com.bikcodeh.melichallenge.data.remote.response

import com.bikcodeh.melichallenge.domain.model.ProductDescription
import com.squareup.moshi.Json

/**
 * Class to get the response from service and map to kotlin class
 */
data class ProductDescriptionResponse(
    @Json(name = "plain_text") val description: String
) {
    fun toDomain(): ProductDescription = ProductDescription(description)
}
