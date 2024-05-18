package ru.virtual.feature_product_list.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.feature_product_list.data.GroceryListRepo
import ru.virtual.feature_product_list.domain.entities.Grocery

class GrocerySearchPagingSource(
    private val repository: GroceryListRepo,
    private val query: String
): RxPagingSource<Int, Grocery>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Grocery>> =
        repository.searchProduct(pageNum = params.key ?: 1, query = query)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, params.key ?: 1) }
            .onErrorReturn { LoadResult.Error(it) }



    private fun toLoadResult(data: List<Grocery>, position: Int): LoadResult<Int, Grocery> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (data.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Grocery>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

}