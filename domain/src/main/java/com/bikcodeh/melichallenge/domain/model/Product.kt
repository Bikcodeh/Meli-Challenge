package com.bikcodeh.melichallenge.domain.model

data class Product(
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val availableQuantity: Int = 0,
    val soldQuantity: Int = 0,
    val condition: String = "",
    val thumbnail: String = "",
    var quantity: Int = 1
)
