package ru.virtual.feature_product_list.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.core_android.Mapper
import ru.virtual.core_db.GroceryListDao
import ru.virtual.core_db.tables.GroceryListAmounts
import ru.virtual.core_db.tables.GroceryListTable
import ru.virtual.core_db.tables.GroceryProduct
import ru.virtual.core_db.tables.GroceryTable
import ru.virtual.core_db.tables.ProductTable
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class DbGroceryListRepo @Inject constructor(
    private val dao: GroceryListDao,
    private val groceryListMapper: Mapper<GroceryList, GroceryListAmounts>,
    private val groceryMapper: Mapper<Grocery, GroceryProduct>
): GroceryListRepo {

    override fun getGroceryListById(listId: Int): Single<GroceryList> = dao.getGroceryListById(listId)
        .subscribeOn(Schedulers.io())
        .map(groceryListMapper::map)

    override fun getGroceryLists(pageNum: Int) = dao.getGroceryLists(page = pageNum)
        .subscribeOn(Schedulers.io())
        .map(groceryListMapper::map)

    override fun getGroceriesFromList(listId: Int, pageNum: Int)= dao.getGroceriesFromList(listId, page= pageNum)
        .subscribeOn(Schedulers.io())
        .map(groceryMapper::map)

    override fun addGroceryList(name: String) =
        dao.addGroceryList(GroceryListTable(name = name))
            .subscribeOn(Schedulers.io())

    override fun removeGroceryList(listId: Int) = dao.removeGroceryList(listId)
        .subscribeOn(Schedulers.io())

    override fun renameGroceryList(listId: Int, newName: String) = dao.renameGroceryList(listId, newName)
        .subscribeOn(Schedulers.io())

    override fun addGroceryToList(listId: Int, productId: Int) = dao.addGroceryToList(GroceryTable(listId, productId))
        .subscribeOn(Schedulers.io())

    override fun removeGroceryFromList(listId: Int, productId: Int): Completable = dao.removeGroceryFromList(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun incrementGroceryAmount(listId: Int, productId: Int) = dao.incrementGroceryAmount(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun decrementGroceryAmount(listId: Int, productId: Int) = dao.decrementGroceryAmount(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun searchProduct(query: String, listId: Int, pageNum: Int)= dao.searchProduct(query, listId, pageNum)
        .subscribeOn(Schedulers.io())
        .map(groceryMapper::map)

    override fun markGroceryInList(listId: Int, productId: Int) = dao.markGroceryInList(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun unMarkGroceryInList(listId: Int, productId: Int) = dao.unMarkGroceryInList(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun addProduct(productName: String): Single<Long> = dao.addProduct(ProductTable(name = productName))
        .subscribeOn(Schedulers.io())
}