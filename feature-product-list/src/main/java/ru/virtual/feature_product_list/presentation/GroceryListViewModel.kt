package ru.virtual.feature_product_list.presentation

import ru.virtual.core_android.RxStateViewModel
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State
import ru.virtual.feature_product_list.domain.entities.GroceryList
import ru.virtual.feature_product_list.domain.usecases.GroceryListUseCase
import javax.inject.Inject

class GroceryListViewModel @Inject constructor(private val groceryListUseCase: GroceryListUseCase):
    RxStateViewModel<GroceryListViewModel.GroceryListState, GroceryListViewModel.GroceryListEffect>() {

    fun getGroceryListsIfNeeded() {
        if(state.value is GroceryListState.Ready) return

        getGroceryLists()
    }

    private fun getGroceryLists() = invokeDisposable {
        groceryListUseCase.getGroceryLists()
            .doOnSubscribe { _state.postValue(GroceryListState.Loading) }
            .subscribe({ groceryLists ->
                if (groceryLists.isEmpty()) {
                    _state.postValue(GroceryListState.DataIsEmpty)
                } else {
                    _state.postValue(GroceryListState.Ready(groceryLists))
                }
            }, {
                _effect.postValue(GroceryListEffect.Error)
            })
    }

    sealed class GroceryListState: State {
        data object Loading : GroceryListState()
        data class Ready(val groceryLists: List<GroceryList>): GroceryListState()
        data object DataIsEmpty : GroceryListState()
    }

    sealed class GroceryListEffect: Effect {
        data object Error: GroceryListEffect()
    }
}