package ru.virtual.feature_fridge.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.core_android.Mapper
import ru.virtual.core_db.FridgeDao
import ru.virtual.core_db.tables.FridgeProductsTable
import ru.virtual.feature_fridge.domain.entities.Product
import javax.inject.Inject

class DbFridgeRepositoryImpl @Inject constructor(
    private val dao: FridgeDao,
    private val productMapper: Mapper<Product, FridgeProductsTable>
) : FridgeRepository {

    override fun getFridgeProducts(pageNum: Int): Single<List<Product>> =
        dao.getFridgeProducts(pageNum)
            .subscribeOn(Schedulers.io())
            .map(productMapper::map)

    override fun removeFromFridge(fridgeId: Int, productId: Int): Completable =
        dao.removeProductFromFridge(productId)
            .subscribeOn(Schedulers.io())
}