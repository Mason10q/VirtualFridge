package ru.virtual.feature_recipes.domain.entities

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val calories: Int,
    val cookingTime: String,
    val difficulty: String,
    val imageUrl: String
)