package ru.virtual.feature_product_list.data

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.core_android.Mapper
import ru.virtual.core_db.GroceryListDao
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.core_db.tables.GroceryProduct
import ru.virtual.core_network.GroceryListApi
import ru.virtual.core_network.dto.GroceryDto
import ru.virtual.core_network.dto.GroceryListDto
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class NetworkGroceryListRepo @Inject constructor(
    private val api: GroceryListApi,
    private val groceryListMapper: Mapper<GroceryList, GroceryListDto>,
    private val groceryMapper: Mapper<Grocery, GroceryDto>
): GroceryListRepo {

    override fun getGroceryLists() = api.getGroceryLists()
        .subscribeOn(Schedulers.io())
        .map(groceryListMapper::map)

    override fun getGroceriesFromList(listId: Int)= api.getGroceriesFromList(listId)
        .subscribeOn(Schedulers.io())
        .map(groceryMapper::map)

    override fun addGroceryList(groceryList: GroceryList) =
        api.addGroceryList(groceryList.name)
            .subscribeOn(Schedulers.io())

    override fun removeGroceryList(listId: Int) = api.removeGroceryList(listId)
        .subscribeOn(Schedulers.io())

    override fun renameGroceryList(listId: Int, newName: String) = api.renameGroceryList(listId, newName)
        .subscribeOn(Schedulers.io())

    override fun addGroceryToList(listId: Int, productId: Int) = api.addGroceryToList(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun incrementGroceryAmount(listId: Int, productId: Int) = api.incrementGroceryAmount(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun decrementGroceryAmount(listId: Int, productId: Int) = api.decrementGroceryAmount(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun searchProduct(query: String)= api.searchProduct(query)
        .subscribeOn(Schedulers.io())
        .map(groceryMapper::map)

    override fun markGroceryInList(listId: Int, productId: Int) = api.markGroceryInList(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun unMarkGroceryInList(listId: Int, productId: Int) = api.unMarkGroceryInList(listId, productId)
        .subscribeOn(Schedulers.io())
}