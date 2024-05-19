package ru.virtual.feature_product_list.domain.usecases

import androidx.paging.Pager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import ru.virtual.feature_product_list.data.GroceryListRepo
import ru.virtual.feature_product_list.data.paging.GroceryListPagingSource
import ru.virtual.feature_product_list.domain.entities.GroceryList
import javax.inject.Inject

class GroceryListUseCaseImpl @Inject constructor(
    private val repository: GroceryListRepo
) : GroceryListUseCase {

    override fun getGroceryLists() = Pager(GroceryListRepo.pagerConfig, initialKey = 1,
        pagingSourceFactory = { GroceryListPagingSource(repository) }
    ).flow

    override fun addGroceryList(name: String): Completable =
        repository.addGroceryList(name)
            .observeOn(AndroidSchedulers.mainThread())

    override fun removeGroceryList(listId: Int): Completable = repository.removeGroceryList(listId)
        .observeOn(AndroidSchedulers.mainThread())

    override fun renameGroceryList(listId: Int, newName: String): Completable =
        repository.renameGroceryList(listId, newName)
            .observeOn(AndroidSchedulers.mainThread())
}