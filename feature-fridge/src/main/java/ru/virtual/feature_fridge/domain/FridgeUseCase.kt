package ru.virtual.feature_fridge.domain

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Completable
import kotlinx.coroutines.flow.Flow
import ru.virtual.feature_fridge.domain.entities.Product

interface FridgeUseCase {

    fun getProducts(): Flow<PagingData<Product>>

    fun removeProduct(fridgeId: Int, productId: Int): Completable

}