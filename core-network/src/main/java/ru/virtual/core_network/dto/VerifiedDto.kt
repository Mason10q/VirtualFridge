package ru.virtual.core_network.dto

import com.google.gson.annotations.SerializedName

data class VerifiedDto(
    @SerializedName("verified")
    val isVerified: Boolean?,
    @SerializedName("familyId")
    val familyId: Int?
)