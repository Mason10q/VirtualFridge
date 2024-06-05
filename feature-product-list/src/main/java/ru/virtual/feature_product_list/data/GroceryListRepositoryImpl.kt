package ru.virtual.feature_product_list.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.feature_product_list.data.db.DbGroceryListRepositoryImpl
import ru.virtual.feature_product_list.data.network.NetworkGroceryListRepositoryImpl
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class GroceryListRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkGroceryListRepositoryImpl,
    private val dbRepository: DbGroceryListRepositoryImpl,
    private val checkIfOnline: () -> Boolean
): GroceryListRepository {

    override fun getGroceryListById(listId: Int): Single<GroceryList> = if(checkIfOnline()) {
        networkRepository.getGroceryListById(listId)
    } else {
        dbRepository.getGroceryListById(listId)
    }

    override fun getGroceryLists(pageNum: Int): Single<List<GroceryList>> = if(checkIfOnline()) {
        networkRepository.getGroceryLists(pageNum)
    } else {
        dbRepository.getGroceryLists(pageNum)
    }

    override fun getGroceriesFromList(listId: Int, pageNum: Int): Single<List<Grocery>> = if(checkIfOnline()) {
        networkRepository.getGroceriesFromList(listId, pageNum)
    } else {
        dbRepository.getGroceriesFromList(listId, pageNum)
    }

    override fun addGroceryList(name: String): Completable = if(checkIfOnline()) {
        networkRepository.addGroceryList(name)
    } else {
        dbRepository.addGroceryList(name)
    }

    override fun removeGroceryList(listId: Int): Completable = if(checkIfOnline()) {
        networkRepository.removeGroceryList(listId)
    } else {
        dbRepository.removeGroceryList(listId)
    }

    override fun renameGroceryList(listId: Int, newName: String): Completable = if(checkIfOnline()) {
        networkRepository.renameGroceryList(listId, newName)
    } else {
        dbRepository.renameGroceryList(listId, newName)
    }

    override fun addGroceryToList(listId: Int, productId: Int): Completable = if(checkIfOnline()) {
        networkRepository.addGroceryToList(listId, productId)
    } else {
        dbRepository.addGroceryToList(listId, productId)
    }

    override fun removeGroceryFromList(listId: Int, productId: Int): Completable = if(checkIfOnline()) {
        networkRepository.removeGroceryFromList(listId, productId)
    } else {
        dbRepository.removeGroceryFromList(listId, productId)
    }

    override fun incrementGroceryAmount(listId: Int, productId: Int): Completable = if(checkIfOnline()) {
        networkRepository.incrementGroceryAmount(listId, productId)
    } else {
        dbRepository.incrementGroceryAmount(listId, productId)
    }

    override fun decrementGroceryAmount(listId: Int, productId: Int): Completable = if(checkIfOnline()) {
        networkRepository.decrementGroceryAmount(listId, productId)
    } else {
        dbRepository.decrementGroceryAmount(listId, productId)
    }

    override fun searchProduct(query: String, listId: Int, pageNum: Int): Single<List<Grocery>> = if(checkIfOnline()) {
        networkRepository.searchProduct(query, listId, pageNum)
    } else {
        dbRepository.searchProduct(query, listId, pageNum)
    }

    override fun setMarkState(listId: Int, productId: Int, state: Boolean): Completable = if(checkIfOnline()) {
        networkRepository.setMarkState(listId, productId, state)
    } else {
        dbRepository.setMarkState(listId, productId, state)
    }

    override fun addProduct(productName: String): Single<Long> = dbRepository.addProduct(productName)
}