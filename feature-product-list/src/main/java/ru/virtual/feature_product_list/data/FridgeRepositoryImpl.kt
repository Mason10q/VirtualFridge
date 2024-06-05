package ru.virtual.feature_product_list.data

import io.reactivex.rxjava3.core.Completable
import ru.virtual.feature_product_list.data.db.DbFridgeRepositoryImpl
import ru.virtual.feature_product_list.data.network.NetworkFridgeRepositoryImpl

class FridgeRepositoryImpl(
    private val networkRepository: NetworkFridgeRepositoryImpl,
    private val dbRepository: DbFridgeRepositoryImpl,
    private val checkIfOnline: () -> Boolean
): FridgeRepository {

    override fun addProductToFridge(productId: Int): Completable = if(checkIfOnline()) {
        networkRepository.addProductToFridge(productId)
    } else {
        dbRepository.addProductToFridge(productId)
    }

    override fun removeProductFromFridge(productId: Int): Completable = if(checkIfOnline()) {
        networkRepository.removeProductFromFridge(productId)
    } else {
        dbRepository.removeProductFromFridge(productId)
    }
}