package ru.virtual.feature_product_list.data

import androidx.paging.PagingConfig
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList

interface GroceryListRepository {

    fun getGroceryListById(listId: Int): Single<GroceryList>

    fun getGroceryLists(pageNum: Int): Single<List<GroceryList>>

    fun getGroceriesFromList(listId: Int, pageNum: Int): Single<List<Grocery>>

    fun addGroceryList(name: String): Completable

    fun removeGroceryList(listId: Int): Completable

    fun renameGroceryList(listId: Int, newName: String): Completable

    fun addGroceryToList(listId: Int, productId: Int): Completable

    fun removeGroceryFromList(listId: Int, productId: Int): Completable

    fun incrementGroceryAmount(listId: Int, productId: Int): Completable

    fun decrementGroceryAmount(listId: Int, productId: Int): Completable

    fun searchProduct(query: String, listId: Int, pageNum: Int): Single<List<Grocery>>

    fun setMarkState(listId: Int, productId: Int, state: Boolean): Completable

    fun addProduct(productName: String): Single<Long>

    companion object {
        val pagerConfig = PagingConfig(
            pageSize = 3,
            initialLoadSize = 3,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
    }

}