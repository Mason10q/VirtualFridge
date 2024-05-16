package ru.virtual.feature_product_list.presentation

import ru.virtual.core_android.RxStateViewModel
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State

class GroceryListViewModel:
    RxStateViewModel<GroceryListViewModel.GroceryListState, GroceryListViewModel.GroceryListEffect>() {

    sealed class GroceryListState: State {

    }

    sealed class GroceryListEffect: Effect {

    }
}