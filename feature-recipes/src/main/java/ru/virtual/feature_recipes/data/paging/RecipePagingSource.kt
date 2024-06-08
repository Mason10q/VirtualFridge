package ru.virtual.feature_recipes.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.feature_recipes.data.RecipeRepository
import ru.virtual.feature_recipes.domain.entities.Recipe

class RecipePagingSource(
    private val repository: RecipeRepository,
    private val query: String?
): RxPagingSource<Int, Recipe>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Recipe>> =
        if(query.isNullOrEmpty()) {
            repository.getRecipes(pageNum = params.key ?: 1)
                .subscribeOn(Schedulers.io())
                .map { toLoadResult(it, params.key ?: 1) }
                .onErrorReturn { LoadResult.Error(it) }
        } else {
            repository.searchRecipes(query, pageNum = params.key ?: 1)
                .subscribeOn(Schedulers.io())
                .map { toLoadResult(it, params.key ?: 1) }
                .onErrorReturn { LoadResult.Error(it) }
        }


    private fun toLoadResult(data: List<Recipe>, position: Int): LoadResult<Int, Recipe> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = if (data.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

}