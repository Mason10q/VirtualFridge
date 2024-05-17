package ru.virtual.feature_product_list.domain.usecases

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList

interface GroceryListUseCase {

    fun getGroceryLists(): Single<List<GroceryList>>

    fun addGroceryList(groceryList: GroceryList): Completable

    fun removeGroceryList(listId: Int): Completable

    fun renameGroceryList(listId: Int, newName: String): Completable

}