package ru.virtual.feature_auth.domain

data class Verified(
    val isVerified: Boolean,
    val familyId: Int
)