package ru.virtual.feature_product_list.presentation.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.rxjava3.cachedIn
import ru.virtual.core_android.RxStateViewModel
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State
import ru.virtual.feature_product_list.domain.entities.GroceryList
import ru.virtual.feature_product_list.domain.usecases.GroceryListUseCase
import javax.inject.Inject

class GroceryListViewModel @Inject constructor(private val groceryListUseCase: GroceryListUseCase) :
    RxStateViewModel<GroceryListViewModel.GroceryListState, GroceryListViewModel.GroceryListEffect>() {

    private val _groceryList = MutableLiveData<GroceryList>()
    val groceryList: LiveData<GroceryList> = _groceryList

    fun getGroceryLists() =
        groceryListUseCase.getGroceryLists()
            .doOnSubscribe { _state.postValue(GroceryListState.Loading) }
            .onErrorComplete()
            .subscribe({
                _state.postValue(GroceryListState.Ready(it))
            }, {
                _effect.postValue(GroceryListEffect.Error)
            })


    fun addGroceryList(name: String) = invokeDisposable {
        groceryListUseCase.addGroceryList(name)
            .subscribe({}, {
                _effect.postValue(GroceryListEffect.Error)
            })
    }

    fun removeGroceryList(listId: Int) = invokeDisposable {
        groceryListUseCase.removeGroceryList(listId)
            .subscribe({}, {
                _effect.postValue(GroceryListEffect.Error)
            })
    }

    fun renameGroceryList(listId: Int, newName: String) = invokeDisposable {
        groceryListUseCase.renameGroceryList(listId, newName)
            .subscribe()
    }

    fun shareGroceryList(context: Context, listId: Int) = invokeDisposable {
        groceryListUseCase.shareGroceryList(context, listId)
    }


    fun getGroceryList(listId: Int) = invokeDisposable {
        groceryListUseCase.getGroceryListById(listId)
            .subscribe({ groceryList ->
                _groceryList.postValue(groceryList)
            }, {
                _effect.postValue(GroceryListEffect.Error)
            })
    }


    sealed class GroceryListState : State {
        data object Loading : GroceryListState()
        data class Ready(val groceryLists: PagingData<GroceryList>) : GroceryListState()
    }

    sealed class GroceryListEffect : Effect {
        data object Error : GroceryListEffect()
    }
}