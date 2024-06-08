package ru.virtual.feature_recipes.domain

import io.reactivex.rxjava3.core.Observable
import ru.virtual.feature_recipes.domain.entities.Filter
import ru.virtual.feature_recipes.domain.entities.RangeFilter
import javax.inject.Inject

class FilterUseCaseImpl @Inject constructor(): FilterUseCase {

    override fun getAllTextFilters(): Observable<List<Filter>> = throw NotImplementedError("")

    override fun getAllRangeFilters(): Observable<List<RangeFilter>> = throw NotImplementedError("")
}