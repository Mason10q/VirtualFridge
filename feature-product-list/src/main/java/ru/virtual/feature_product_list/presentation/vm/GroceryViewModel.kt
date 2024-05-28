package ru.virtual.feature_product_list.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.onStart
import ru.virtual.core_android.RxStateViewModel
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State
import ru.virtual.feature_product_list.domain.entities.Grocery
import ru.virtual.feature_product_list.domain.entities.GroceryList
import ru.virtual.feature_product_list.domain.usecases.GroceryListUseCase
import ru.virtual.feature_product_list.domain.usecases.GroceryUseCase
import javax.inject.Inject

class GroceryViewModel @Inject constructor(private val groceryUseCase: GroceryUseCase, private val groceryListUseCase: GroceryListUseCase):
    RxStateViewModel<GroceryViewModel.GroceryState, GroceryViewModel.GroceryEffect>() {

    private val _groceryList = MutableLiveData<GroceryList>()
    val groceryList: LiveData<GroceryList> = _groceryList

    private val _addedProductId = MutableLiveData<Long>()
    val addedProductId: LiveData<Long> = _addedProductId

    var groceriesAmount: Int? = null

    suspend fun getGroceriesIfNeeded(listId: Int) {
        if (state.value is GroceryState.Ready) return

        getAllGroceries(listId)
    }

    private suspend fun getAllGroceries(listId: Int) = groceryUseCase.getGroceries(listId)
        .cachedIn(viewModelScope)
        .onStart { _state.postValue(GroceryState.Loading) }
        .collect { groceryLists ->
            _state.postValue(GroceryState.Ready(groceryLists))
        }

    suspend fun searchGroceries(query: String, listId: Int) = groceryUseCase.searchProducts(query, listId)
        .cachedIn(viewModelScope)
        .onStart { _state.postValue(GroceryState.Loading) }
        .collect { groceries ->
            _state.postValue(GroceryState.Ready(groceries))
        }

    suspend fun getListGroceries(listId: Int) = groceryUseCase.getListGroceries(listId)
        .cachedIn(viewModelScope)
        .onStart { _state.postValue(GroceryState.Loading) }
        .collect{ groceries ->
            _state.postValue(GroceryState.Ready(groceries))
        }

    fun getGroceryList(listId: Int) = groceryListUseCase.getGroceryListById(listId)
        .subscribe({ groceryList ->
            groceriesAmount = groceryList.productsAmount
           _groceryList.postValue(groceryList)
        }, {})

    fun incrementGroceryAmount(listId: Int, grocery: Grocery) =
        groceryUseCase.incrementGroceryAmount(listId, grocery)
            .subscribe()

    fun decrementGroceryAmount(listId: Int, grocery: Grocery) =
        groceryUseCase.decrementGroceryAmount(listId, grocery)
            .subscribe()

    fun setMarkState(listId: Int, productId: Int, state: Boolean) =
        groceryUseCase.setMarkState(listId, productId, state)
            .subscribe({}, {
                Log.d("asd", it.message.toString())
            })

    fun addProduct(productName: String) =
        groceryUseCase.addProduct(productName)
            .subscribe({ id ->
                Log.d("asd", "viewModel $id")
               _addedProductId.postValue(id)
            }, {})


    sealed class GroceryState : State {
        data object Loading : GroceryState()
        data class Ready(val groceries: PagingData<Grocery>) : GroceryState()
    }

    sealed class GroceryEffect : Effect {
        data object Error : GroceryEffect()
    }
}