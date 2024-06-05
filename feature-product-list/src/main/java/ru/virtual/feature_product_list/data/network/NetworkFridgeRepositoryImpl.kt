package ru.virtual.feature_product_list.data.network

import io.reactivex.rxjava3.core.Completable
import ru.virtual.core_network.FridgeApi
import ru.virtual.feature_product_list.data.FridgeRepository
import javax.inject.Inject

class NetworkFridgeRepositoryImpl @Inject constructor(
    private val api: FridgeApi,
    private val familyId: Int
): FridgeRepository {

    override fun addProductToFridge(productId: Int): Completable = api.addProductToFridge(familyId, productId)

    override fun removeProductFromFridge(productId: Int): Completable = api.removeProductFromFridge(familyId, productId)
}