package ru.virtual.feature_recipes.domain

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import ru.virtual.feature_recipes.domain.entities.Filter
import ru.virtual.feature_recipes.domain.entities.RangeFilter

interface FilterUseCase {

    fun getAllTextFilters(): Observable<List<Filter>>

    fun getAllRangeFilters(): Observable<List<RangeFilter>>

}