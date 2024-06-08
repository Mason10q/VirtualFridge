package ru.virtual.core_network.dto

import com.google.gson.annotations.SerializedName

data class RecipeDto (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("recipe_name")
    val recipeName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("calories")
    val recipeCalories: Int?,
    @SerializedName("total_cooking_time")
    val cookingTime: String?,
    @SerializedName("image_url")
    val recipeImageUrl: String?,
    @SerializedName("difficulty_name")
    val difficultyName: String?
)