package ru.virtual.feature_recipes.domain.entities

data class RecipeProduct(
    val productName: String,
    val amount: String,
    val isInFridge: Boolean
)