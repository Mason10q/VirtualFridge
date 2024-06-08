package ru.virtual.feature_recipes.data

import androidx.paging.PagingConfig
import io.reactivex.rxjava3.core.Single
import ru.virtual.core_network.dto.RecipeDto
import ru.virtual.core_network.dto.RecipeProductDto
import ru.virtual.feature_recipes.domain.entities.Recipe
import ru.virtual.feature_recipes.domain.entities.RecipeProduct
import java.util.Locale.Category

interface RecipeRepository {
    fun getRecipes(pageNum: Int): Single<List<Recipe>>

    fun searchRecipes(query: String, pageNum: Int): Single<List<Recipe>>

    fun getRecipe(recipeId: Int):Single<Recipe>

    fun getRecipeProducts(recipeId: Int): Single<List<RecipeProduct>>

    companion object {
        val pagerConfig = PagingConfig(
            pageSize = 3,
            initialLoadSize = 3,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
    }

}