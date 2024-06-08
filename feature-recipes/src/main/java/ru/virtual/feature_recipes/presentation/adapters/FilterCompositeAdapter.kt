package ru.virtual.feature_recipes.presentation.adapters

import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import ru.virtual.feature_recipes.domain.entities.Filter
import ru.virtual.feature_recipes.domain.entities.RangeFilter

class FilterCompositeAdapter(filterAdapter: FilterAdapter, rangeFilterAdapter: RangeFilterAdapter): CompositeDelegateAdapter(
    filterAdapter,
    rangeFilterAdapter,
    FilterTitleAdapter()
) {

    override fun swapData(data: List<Any>) {

        val _data = arrayListOf<Any>()

        _data.addAll(data.filterIsInstance<RangeFilter>())

        data.filterIsInstance<Filter>().forEach{ filter ->
            _data.add(filter.title)
            _data.addAll(filter.filterNames)
        }

        super.swapData(_data)
    }

    fun notifyItemChanged(item: Any) {
        notifyItemChanged(adapterState.data.indexOf(item))
    }

    fun getItem(position: Int) = adapterState.data[position]

}