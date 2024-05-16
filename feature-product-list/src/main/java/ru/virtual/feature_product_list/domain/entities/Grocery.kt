package ru.virtual.feature_product_list.domain.entities

data class Grocery(
    val productId: Int,
    val name: String,
    val amount: Int,
    val marked: Boolean
)