package ru.virtual.feature_product_list.data.network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.core_android.Mapper
import ru.virtual.core_network.GroceryListApi
import ru.virtual.core_network.dto.GroceryDto
import ru.virtual.core_network.dto.GroceryListDto
import ru.virtual.feature_product_list.data.GroceryListRepository
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class NetworkGroceryListRepositoryImpl @Inject constructor(
    private val api: GroceryListApi,
    private val groceryListMapper: Mapper<GroceryList, GroceryListDto>,
    private val groceryMapper: Mapper<Grocery, GroceryDto>,
    private val familyId: Int
) : GroceryListRepository {

    override fun getGroceryListById(listId: Int): Single<GroceryList> = api.getGroceryListById(listId)
        .subscribeOn(Schedulers.io())
        .map(groceryListMapper::map)

    override fun getGroceryLists(pageNum: Int) = api.getGroceryLists(familyId, page = pageNum)
        .subscribeOn(Schedulers.io())
        .map(groceryListMapper::map)

    override fun getGroceriesFromList(listId: Int, pageNum: Int) =
        api.getGroceriesFromList(page = pageNum, listId = listId)
            .subscribeOn(Schedulers.io())
            .map(groceryMapper::map)

    override fun addGroceryList(name: String) =
        api.addGroceryList(familyId, name)
            .subscribeOn(Schedulers.io())

    override fun removeGroceryList(listId: Int) = api.removeGroceryList(listId)
        .subscribeOn(Schedulers.io())

    override fun renameGroceryList(listId: Int, newName: String) =
        api.renameGroceryList(listId, newName)
            .subscribeOn(Schedulers.io())

    override fun addGroceryToList(listId: Int, productId: Int) =
        api.addGroceryToList(listId, productId)
            .subscribeOn(Schedulers.io())

    override fun removeGroceryFromList(listId: Int, productId: Int): Completable = api.removeGroceryFromList(listId, productId)
        .subscribeOn(Schedulers.io())

    override fun incrementGroceryAmount(listId: Int, productId: Int) =
        api.incrementGroceryAmount(listId, productId)
            .subscribeOn(Schedulers.io())

    override fun decrementGroceryAmount(listId: Int, productId: Int) =
        api.decrementGroceryAmount(listId, productId)
            .subscribeOn(Schedulers.io())

    override fun searchProduct(query: String, listId: Int, pageNum: Int) =
        api.searchProduct(query, listId, pageNum)
            .subscribeOn(Schedulers.io())
            .map(groceryMapper::map)

    override fun setMarkState(listId: Int, productId: Int, state: Boolean): Completable {
        return if(state) api.markGrocery(listId, productId)
        else api.unMarkGrocery(listId, productId)
    }

    override fun addProduct(productName: String): Single<Long> = Single.never()
}