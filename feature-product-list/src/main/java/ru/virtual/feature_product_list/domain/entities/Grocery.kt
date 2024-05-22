package ru.virtual.feature_product_list.domain.entities

data class Grocery(
    val productId: Int,
    val name: String,
    var amount: Int,
    val marked: Boolean
)