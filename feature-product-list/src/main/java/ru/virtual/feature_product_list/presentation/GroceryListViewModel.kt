package ru.virtual.feature_product_list.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import ru.virtual.core_android.RxStateViewModel
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList
import ru.virtual.feature_product_list.domain.usecases.GroceryListUseCase
import javax.inject.Inject

class GroceryListViewModel @Inject constructor(private val groceryListUseCase: GroceryListUseCase):
    RxStateViewModel<GroceryListViewModel.GroceryListState, GroceryListViewModel.GroceryListEffect>() {

    suspend fun getGroceryListsIfNeeded() {
        if(state.value is GroceryListState.Ready) return

        getGroceryLists()
    }

    private suspend fun getGroceryLists() =
        groceryListUseCase.getGroceryLists()
            .cachedIn(viewModelScope)
            .onStart { _state.postValue(GroceryListState.Loading) }
            .collect{ groceryLists ->
                _state.postValue(GroceryListState.Ready(groceryLists))
            }

    fun addGroceryList(name: String) = groceryListUseCase.addGroceryList(name)
        .subscribe({}, {
            _effect.postValue(GroceryListEffect.Error)
        })

    sealed class GroceryListState: State {
        data object Loading : GroceryListState()
        data class Ready(val groceryLists: PagingData<GroceryList>): GroceryListState()
    }

    sealed class GroceryListEffect: Effect {
        data object Error: GroceryListEffect()
    }
}