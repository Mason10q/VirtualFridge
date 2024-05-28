package ru.virtual.feature_fridge.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ru.virtual.core_android.RxStateViewModel
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State
import ru.virtual.feature_fridge.domain.FridgeUseCase
import ru.virtual.feature_fridge.domain.entities.Product
import javax.inject.Inject

class FridgeViewModel @Inject constructor(private val fridgeUseCase: FridgeUseCase) :
    RxStateViewModel<FridgeViewModel.FridgeState, FridgeViewModel.FridgeEffect>() {


    suspend fun getProducts() = fridgeUseCase.getProducts()
        .cachedIn(viewModelScope)
        .collect{
            _state.postValue(FridgeState.Ready(it))
        }

    fun removeProduct(fridgeId: Int, productId: Int) = fridgeUseCase.removeProduct(fridgeId, productId)
        .subscribe()

    sealed class FridgeState : State {
        data object Loading : FridgeState()

        data class Ready(val products: PagingData<Product>) : FridgeState()
    }

    sealed class FridgeEffect : Effect {
        data object Error : FridgeEffect()
    }


}