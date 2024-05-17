package ru.virtual.feature_product_list.domain.usecases

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.feature_product_list.data.GroceryListRepo
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class GroceryListUseCaseImpl @Inject constructor(
    private val repository: GroceryListRepo
) : GroceryListUseCase {

    override fun getGroceryLists(): Single<List<GroceryList>> = repository.getGroceryLists()
        .observeOn(AndroidSchedulers.mainThread())

    override fun addGroceryList(groceryList: GroceryList): Completable =
        repository.addGroceryList(groceryList)
            .observeOn(AndroidSchedulers.mainThread())

    override fun removeGroceryList(listId: Int): Completable = repository.removeGroceryList(listId)
        .observeOn(AndroidSchedulers.mainThread())

    override fun renameGroceryList(listId: Int, newName: String): Completable =
        repository.renameGroceryList(listId, newName)
            .observeOn(AndroidSchedulers.mainThread())
}