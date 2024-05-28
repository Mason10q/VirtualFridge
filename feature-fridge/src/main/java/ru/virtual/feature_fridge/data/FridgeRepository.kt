package ru.virtual.feature_fridge.data

import androidx.paging.PagingConfig
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.feature_fridge.domain.entities.Product

interface FridgeRepository {

    fun getFridgeProducts(pageNum: Int): Single<List<Product>>

    fun removeFromFridge(fridgeId: Int, productId: Int): Completable

    companion object {
        val pagerConfig = PagingConfig(
            pageSize = 10,
            initialLoadSize = 3,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
    }
}