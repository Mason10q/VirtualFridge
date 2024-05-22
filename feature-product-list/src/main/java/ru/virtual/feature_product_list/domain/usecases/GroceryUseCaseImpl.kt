package ru.virtual.feature_product_list.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import ru.virtual.feature_product_list.data.GroceryListRepo
import ru.virtual.feature_product_list.data.paging.GroceryPagingSource
import ru.virtual.feature_product_list.data.paging.GrocerySearchPagingSource
import ru.virtual.feature_product_list.domain.entities.Grocery
import javax.inject.Inject

class GroceryUseCaseImpl @Inject constructor(private val repository: GroceryListRepo): GroceryUseCase {

    override fun getGroceries(listId: Int): Flow<PagingData<Grocery>> = Pager(GroceryListRepo.pagerConfig, initialKey = 1,
        pagingSourceFactory = { GrocerySearchPagingSource(repository, listId, "") }).flow

    override fun removeGrocery(listId: Int, productId: Int): Completable = repository.removeGroceryFromList(listId, productId)
        .observeOn(AndroidSchedulers.mainThread())

    override fun searchProducts(query: String, listId: Int): Flow<PagingData<Grocery>> = Pager(GroceryListRepo.pagerConfig, initialKey = 1,
        pagingSourceFactory = { GrocerySearchPagingSource(repository, listId, query) }).flow

    override fun incrementGroceryAmount(listId: Int, productId: Int): Completable =
        repository.incrementGroceryAmount(listId, productId)
            .observeOn(AndroidSchedulers.mainThread())

    override fun getListGroceries(listId: Int): Flow<PagingData<Grocery>> = Pager(GroceryListRepo.pagerConfig, initialKey = 1,
        pagingSourceFactory = { GroceryPagingSource(repository, listId) }).flow

    override fun decrementGroceryAmount(listId: Int, productId: Int): Completable =
        repository.decrementGroceryAmount(listId, productId)
            .observeOn(AndroidSchedulers.mainThread())

    override fun addProduct(productName: String): Single<Long> = repository.addProduct(productName)
        .observeOn(AndroidSchedulers.mainThread())

    override fun addGrocery(listId: Int, productId: Int): Completable = repository.addGroceryToList(listId, productId)
        .observeOn(AndroidSchedulers.mainThread())

    override fun markGrocery(listId: Int, productId: Int): Completable =
        repository.markGroceryInList(listId, productId)
            .observeOn(AndroidSchedulers.mainThread())

    override fun unMarkGrocery(listId: Int, productId: Int): Completable =
        repository.unMarkGroceryInList(listId, productId)
            .observeOn(AndroidSchedulers.mainThread())
}