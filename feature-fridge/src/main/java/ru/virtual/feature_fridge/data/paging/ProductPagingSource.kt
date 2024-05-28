package ru.virtual.feature_fridge.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.feature_fridge.data.FridgeRepository
import ru.virtual.feature_fridge.domain.entities.Product

class ProductPagingSource(private val repository: FridgeRepository)
    : RxPagingSource<Int, Product>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Product>> =
        repository.getFridgeProducts(pageNum = params.key ?: 1)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, params.key ?: 1) }
            .onErrorReturn { LoadResult.Error(it) }



    private fun toLoadResult(data: List<Product>, position: Int): LoadResult<Int, Product> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = if (data.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

}