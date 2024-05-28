package ru.virtual.feature_fridge.data

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.virtual.core_android.Mapper
import ru.virtual.core_network.FridgeApi
import ru.virtual.core_network.dto.FridgeProductDto
import ru.virtual.feature_fridge.domain.entities.Product
import javax.inject.Inject

class NetworkFridgeRepositoryImpl @Inject constructor(
    private val api: FridgeApi,
    private val productMapper: Mapper<Product, FridgeProductDto>,
    private val familyId: Int
) : FridgeRepository {

    override fun getFridgeProducts(pageNum: Int): Single<List<Product>> =
        api.getFridgeProducts(familyId, pageNum)
            .subscribeOn(Schedulers.io())
            .map(productMapper::map)


    override fun removeFromFridge(fridgeId: Int, productId: Int): Completable =
        api.removeProductFromFridge(fridgeId, productId)
            .subscribeOn(Schedulers.io())
}