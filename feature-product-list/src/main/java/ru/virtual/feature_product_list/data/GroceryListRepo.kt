package ru.virtual.feature_product_list.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList

interface GroceryListRepo {

    fun getGroceryLists(): Single<List<GroceryList>>

    fun getGroceriesFromList(listId: Int): Single<List<Grocery>>

    fun addGroceryList(groceryList: GroceryList): Completable

    fun removeGroceryList(listId: Int): Completable

    fun renameGroceryList(listId: Int, newName: String): Completable

    fun addGroceryToList(listId: Int, productId: Int): Completable

    fun incrementGroceryAmount(listId: Int, productId: Int): Completable

    fun decrementGroceryAmount(listId: Int, productId: Int): Completable

    fun searchProduct(query: String): Single<List<Grocery>>

    fun markGroceryInList(listId: Int, productId: Int): Completable

    fun unMarkGroceryInList(listId: Int, productId: Int): Completable

}