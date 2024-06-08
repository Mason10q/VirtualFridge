package ru.virtual.feature_recipes.domain

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import ru.virtual.feature_recipes.domain.entities.Recipe
import ru.virtual.feature_recipes.domain.entities.RecipeProduct

interface RecipeUseCase {

    fun getRecipes(filters: Map<String, List<String>>): Observable<PagingData<Recipe>>

    fun searchRecipes(query: String, filters: Map<String, List<String>>): Flow<PagingData<Recipe>>
    fun getRecipe(recipeId: Int): Single<Recipe>

    fun getRecipeProducts(recipeId: Int): Single<List<RecipeProduct>>
}