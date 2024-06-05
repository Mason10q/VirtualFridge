package ru.virtual.feature_fridge.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.virtual.feature_fridge.domain.entities.Product
import javax.inject.Inject

class FridgeRepositoryImpl @Inject constructor(
    private val dbRepository: DbFridgeRepositoryImpl,
    private val networkRepository: NetworkFridgeRepositoryImpl,
    private val checkOnline: () -> Boolean
): FridgeRepository {

    override fun getFridgeProducts(pageNum: Int): Single<List<Product>> = if(checkOnline()) {
        networkRepository.getFridgeProducts(pageNum)
    } else {
        dbRepository.getFridgeProducts(pageNum)
    }

    override fun removeFromFridge(fridgeId: Int, productId: Int): Completable = if(checkOnline()) {
        networkRepository.removeFromFridge(fridgeId, productId)
    } else {
        dbRepository.removeFromFridge(fridgeId, productId)
    }
}