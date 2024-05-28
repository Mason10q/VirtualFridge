package ru.virtual.feature_product_list.data

import io.reactivex.rxjava3.core.Completable

interface FridgeRepository {

    fun addProductToFridge(productId: Int): Completable

    fun removeProductFromFridge(productId: Int): Completable

}