package ru.virtual.feature_product_list.domain.entities

data class GroceryList(
    val id: Int,
    val name: String,
    val productsAmount: Int,
    val productsMarked: Int
)