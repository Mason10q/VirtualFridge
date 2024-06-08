package ru.virtual.feature_recipes.domain.mappers

import ru.virtual.core_android.Mapper
import ru.virtual.core_network.dto.RecipeDto
import ru.virtual.feature_recipes.BuildConfig
import ru.virtual.feature_recipes.domain.entities.Recipe
import javax.inject.Inject

class RecipeMapper @Inject constructor() : Mapper<Recipe, RecipeDto> {

    override fun map(item: RecipeDto): Recipe = Recipe(
        item.id ?: -1,
        item.recipeName ?: "",
        item.description ?: "",
        item.recipeCalories ?: 0,
        item.cookingTime ?: "",
        item.difficultyName ?: "",
        ("${BuildConfig.ENDPOINT_URL}/${item.recipeImageUrl}")
    )

}