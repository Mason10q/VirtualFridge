package ru.virtual.core_network.dto

import com.google.gson.annotations.SerializedName

data class GroceryDto(
    @SerializedName("productId")
    val productId: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("marked")
    val marked: Boolean?
)