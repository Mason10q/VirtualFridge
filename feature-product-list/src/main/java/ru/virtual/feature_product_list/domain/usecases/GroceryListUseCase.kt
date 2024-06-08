package ru.virtual.feature_product_list.domain.usecases

import android.content.Context
import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.Flow
import ru.virtual.feature_product_list.domain.entities.GroceryList

interface GroceryListUseCase {

    fun getGroceryLists(): Observable<PagingData<GroceryList>>

    fun addGroceryList(name: String): Completable

    fun removeGroceryList(listId: Int): Completable

    fun renameGroceryList(listId: Int, newName: String): Completable

    fun shareGroceryList(context: Context, listId: Int): Disposable

    fun getGroceryListById(listId: Int): Single<GroceryList>

}