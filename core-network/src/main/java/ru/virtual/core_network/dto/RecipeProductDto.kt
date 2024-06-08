package ru.virtual.core_network.dto

import com.google.gson.annotations.SerializedName

data class RecipeProductDto(
    @SerializedName("product_name")
    val productName: String?,
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("is_in_fridge")
    private val isInFridgeInt: Int?
) {
    val isInFridge: Boolean = isInFridgeInt != 0
}