package ru.virtual.feature_recipes.domain

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import ru.virtual.feature_recipes.data.RecipeRepository
import ru.virtual.feature_recipes.data.paging.RecipePagingSource
import ru.virtual.feature_recipes.domain.entities.Recipe
import ru.virtual.feature_recipes.domain.entities.RecipeProduct
import javax.inject.Inject

class RecipeUseCaseImpl @Inject constructor(
    private val repository: RecipeRepository
): RecipeUseCase {

    override fun getRecipes(filters: Map<String, List<String>>): Observable<PagingData<Recipe>> =
        Pager(RecipeRepository.pagerConfig, initialKey = null,
            pagingSourceFactory = { RecipePagingSource(repository, null) }
        ).observable

    override fun searchRecipes(
        query: String,
        filters: Map<String, List<String>>
    ): Flow<PagingData<Recipe>> = Pager(RecipeRepository.pagerConfig, initialKey = null,
        pagingSourceFactory = { RecipePagingSource(repository, query) }
    ).flow

    override fun getRecipe(recipeId: Int): Single<Recipe> = repository.getRecipe(recipeId)

    override fun getRecipeProducts(recipeId: Int): Single<List<RecipeProduct>> = repository.getRecipeProducts(recipeId)
}