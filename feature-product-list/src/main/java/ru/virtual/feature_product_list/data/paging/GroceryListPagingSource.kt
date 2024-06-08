package ru.virtual.feature_product_list.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.feature_product_list.data.GroceryListRepository
import ru.virtual.feature_product_list.domain.entities.GroceryList

class GroceryListPagingSource(
    private val repository: GroceryListRepository,
): RxPagingSource<Int, GroceryList>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, GroceryList>> =
        repository.getGroceryLists(pageNum = params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { toLoadResult(it, params.key ?: 1) }
            .onErrorReturn { LoadResult.Error(it) }



    private fun toLoadResult(data: List<GroceryList>, position: Int): LoadResult<Int, GroceryList> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = if (data.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, GroceryList>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

}