package ru.virtual.feature_product_list.domain.usecases

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import ru.virtual.feature_product_list.domain.entities.Grocery

interface GroceryUseCase {

    fun getGroceries(listId: Int): Flow<PagingData<Grocery>>
    fun searchProducts(query: String, listId: Int): Flow<PagingData<Grocery>>

    fun incrementGroceryAmount(listId: Int, grocery: Grocery): Completable

    fun decrementGroceryAmount(listId: Int, grocery: Grocery): Completable

    fun getListGroceries(listId: Int): Flow<PagingData<Grocery>>

    fun addProduct(productName: String): Single<Long>

    fun addGrocery(listId: Int, productId: Int): Completable

    fun removeGrocery(listId: Int, productId: Int): Completable

    fun setMarkState(listId: Int, productId: Int, state: Boolean): Completable

}