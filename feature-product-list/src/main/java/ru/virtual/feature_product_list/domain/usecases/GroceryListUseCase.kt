package ru.virtual.feature_product_list.domain.usecases

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Completable
import kotlinx.coroutines.flow.Flow
import ru.virtual.feature_product_list.domain.entities.GroceryList

interface GroceryListUseCase {

    fun getGroceryLists(): Flow<PagingData<GroceryList>>

    fun addGroceryList(name: String): Completable

    fun removeGroceryList(listId: Int): Completable

    fun renameGroceryList(listId: Int, newName: String): Completable

}