package ru.virtual.core_network.dto

import com.google.gson.annotations.SerializedName

data class FridgeProductDto(
    @SerializedName("fridge_id")
    val id: Int?,
    @SerializedName("product_name")
    val name: String?,
    @SerializedName("product_id")
    val productId: Int?
)