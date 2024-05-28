package ru.virtual.feature_product_list.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.core_db.FridgeDao
import javax.inject.Inject

class FridgeRepositoryImpl @Inject constructor(private val dao: FridgeDao): FridgeRepository {

    override fun addProductToFridge(productId: Int): Completable = dao.addProductToFridge(productId)
        .subscribeOn(Schedulers.io())

    override fun removeProductFromFridge(productId: Int): Completable = dao.removeProductFromFridge(productId)
        .subscribeOn(Schedulers.io())
}