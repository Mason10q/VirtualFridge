package ru.virtual.feature_recipes.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.rxjava3.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.launch
import ru.virtual.core_android.RxStateViewModel
import ru.virtual.core_android.states.Effect
import ru.virtual.core_android.states.State
import ru.virtual.feature_recipes.domain.entities.Filter
import ru.virtual.feature_recipes.domain.FilterUseCase
import ru.virtual.feature_recipes.domain.RecipeUseCase
import ru.virtual.feature_recipes.domain.entities.RangeFilter
import ru.virtual.feature_recipes.domain.entities.Recipe
import ru.virtual.feature_recipes.domain.entities.RecipeProduct
import javax.inject.Inject

class RecipesViewModel @Inject constructor(
    //private val filterUseCase: FilterUseCase,
    private val recipeUseCase: RecipeUseCase
): RxStateViewModel<RecipesViewModel.RecipesState, RecipesViewModel.RecipesEffect>() {


    private val _filters = MutableLiveData<List<Filter>>()
    val filters: LiveData<List<Filter>> = _filters

    private val _rangeFilters = MutableLiveData<List<RangeFilter>>()
    val rangeFilters: LiveData<List<RangeFilter>> = _rangeFilters

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> = _recipe

    private val _recipeProducts = MutableLiveData<List<RecipeProduct>>()
    val recipeProducts: LiveData<List<RecipeProduct>> = _recipeProducts

    private val filtersMap = HashMap<String, MutableList<String>>()

    private val rangeMap = HashMap<String, List<Float>>()

    private var searchJob: Job? = null

//    fun getAllTextFilters() = filterUseCase.getAllTextFilters()
//        .subscribe({filters ->
//            _filters.postValue(filters)
//        }, {})

//    fun getAllRangeFilters() = filterUseCase.getAllRangeFilters()
//        .subscribe({
//            _rangeFilters.postValue(it)
//        }, {})

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getRecipes() = recipeUseCase.getRecipes(filtersMap)
        .cachedIn(viewModelScope)
        .doOnSubscribe { _state.postValue(RecipesState.Loading) }
        .subscribe({
           _state.postValue(RecipesState.Ready(it))
        }, {})

    fun searchRecipes(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(1000)

            recipeUseCase.searchRecipes(query, filtersMap)
                .cachedIn(viewModelScope)
                .flowOn(Dispatchers.IO)
                .collect {
                    _state.postValue(RecipesState.Ready(it))
                }
        }
    }

    fun getRecipe(recipeId: Int) = recipeUseCase.getRecipe(recipeId)
        .subscribe({
           _recipe.postValue(it)
        }, {
            Log.d("asd", it.message.toString())
        })

    fun getRecipeProducts(recipeId: Int) = recipeUseCase.getRecipeProducts(recipeId)
        .subscribe({
            _recipeProducts.postValue(it)
        }, {
            Log.d("asd", it.message.toString())
        })


    fun addFilter(key: String, value: String) {
        if(filtersMap.containsKey(key)){
            filtersMap[key]?.add(value)
        } else {
            filtersMap[key] = mutableListOf(value)
        }
    }

    fun deleteFilter(key: String, value: String) {
        filtersMap[key]?.remove(value)
    }

    fun addRangeFilter(key: String, values: List<Float>) {
        rangeMap[key] = values
        filtersMap[key] = mutableListOf("${values[0]}-${values[1]}")
    }

    fun getFilterMap(): Map<String, List<String>> = filtersMap

    fun getRangeMap(): Map<String, List<Float>> = rangeMap

    sealed class RecipesState : State {
        data object Loading : RecipesState()
        data class Ready(val recipes: PagingData<Recipe>) : RecipesState()
    }

    sealed class RecipesEffect : Effect {
        data object Error : RecipesEffect()
    }

}