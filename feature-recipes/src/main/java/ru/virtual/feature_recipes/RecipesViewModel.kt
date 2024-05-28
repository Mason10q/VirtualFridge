package ru.virtual.feature_recipes

import ru.virtual.core_android.RxStateViewModel
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State

class RecipesViewModel: RxStateViewModel<RecipesViewModel.RecipesState, RecipesViewModel.RecipesEffect>() {

    sealed class RecipesState : State {
        data object Loading : RecipesState()
        data class Ready(val groceryLists: PagingData<GroceryList>) : RecipesState()
    }

    sealed class RecipesEffect : Effect {
        data object Error : RecipesEffect()
    }

}