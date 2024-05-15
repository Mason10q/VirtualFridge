package ru.virtual.core_network.dto

import com.google.gson.annotations.SerializedName

data class GroceryListDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("products_amount")
    val productsAmount: Int?,
    @SerializedName("products_marked")
    val productsMarked: Int?
)