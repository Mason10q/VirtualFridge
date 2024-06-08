package ru.virtual.feature_recipes.data

import io.reactivex.rxjava3.core.Single
import ru.virtual.core_android.Mapper
import ru.virtual.core_network.RecipeApi
import ru.virtual.core_network.dto.RecipeDto
import ru.virtual.core_network.dto.RecipeProductDto
import ru.virtual.feature_recipes.domain.entities.Recipe
import ru.virtual.feature_recipes.domain.entities.RecipeProduct
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val recipeMapper: Mapper<Recipe, RecipeDto>,
    private val recipeProductMapper: Mapper<RecipeProduct, RecipeProductDto>,
    private val familyId: Int
): RecipeRepository {

    override fun getRecipes(pageNum: Int): Single<List<Recipe>> = api.getRecipes(pageNum)
        .map(recipeMapper::map)

    override fun searchRecipes(query: String, pageNum: Int): Single<List<Recipe>> = api.searchRecipes(query, pageNum)
        .map(recipeMapper::map)

    override fun getRecipe(recipeId: Int): Single<Recipe> = api.getRecipe(recipeId)
        .map(recipeMapper::map)

    override fun getRecipeProducts(recipeId: Int): Single<List<RecipeProduct>> = api.getRecipeProducts(recipeId, familyId)
        .map(recipeProductMapper::map)
}