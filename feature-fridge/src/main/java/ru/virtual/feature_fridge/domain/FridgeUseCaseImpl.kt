package ru.virtual.feature_fridge.domain

import androidx.paging.Pager
import androidx.paging.PagingData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import kotlinx.coroutines.flow.Flow
import ru.virtual.feature_fridge.data.FridgeRepository
import ru.virtual.feature_fridge.data.paging.ProductPagingSource
import ru.virtual.feature_fridge.domain.entities.Product
import javax.inject.Inject

class FridgeUseCaseImpl @Inject constructor(private val repository: FridgeRepository): FridgeUseCase {

    override fun getProducts(): Flow<PagingData<Product>> = Pager(FridgeRepository.pagerConfig, initialKey = 1,
        pagingSourceFactory = { ProductPagingSource(repository) }
    ).flow

    override fun removeProduct(fridgeId: Int, productId: Int): Completable = repository.removeFromFridge(fridgeId, productId)
        .observeOn(AndroidSchedulers.mainThread())
}