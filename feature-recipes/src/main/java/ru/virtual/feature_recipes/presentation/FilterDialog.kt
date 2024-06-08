package ru.virtual.feature_recipes.presentation

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ru.virtual.core_android.ViewModelFactory
import ru.virtual.core_android.di.AndroidModule
import ru.virtual.core_android.ui.BaseBottomSheetDialogFragment
import ru.virtual.core_android.ui.utils.addItemMargins
import ru.virtual.feature_recipes.databinding.DialogFilterBinding
import ru.virtual.feature_recipes.presentation.adapters.FilterCompositeAdapter
import ru.virtual.feature_recipes.domain.entities.FilterName
import ru.virtual.feature_recipes.domain.entities.RangeFilter
import ru.virtual.feature_recipes.presentation.adapters.FilterAdapter
import ru.virtual.feature_recipes.presentation.adapters.RangeFilterAdapter
import javax.inject.Inject

private const val COLLAPSED_HEIGHT = 228
class FilterDialog : BaseBottomSheetDialogFragment<DialogFilterBinding>(DialogFilterBinding::class.java) {

    private var onCancelListener = {}

    @Inject lateinit var factory: ViewModelFactory

    private val viewModel by lazy { ViewModelProvider(requireActivity(), factory)[RecipesViewModel::class.java] }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        //inject(context)
    }
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelListener.invoke()
    }

    fun setOnCancelListener(listener: () -> Unit) {
        onCancelListener = listener
    }

//    private fun inject(context: Context) = DaggerRecipeComponent.builder()
//        .androidModule(AndroidModule(context))
//        .build()
//        .inject(this)


    override fun setUpViews(view: View) {
        setupAdapter()
    }

    private fun setupAdapter() {
        val filterAdapter = FilterAdapter()
        val rangeFilterAdapter = RangeFilterAdapter()

        val adapter = FilterCompositeAdapter(filterAdapter, rangeFilterAdapter)

        filterAdapter.setCheckedItems(viewModel.getFilterMap())
        rangeFilterAdapter.setSavedRanges(viewModel.getRangeMap())

        filterAdapter.setOnItemClickListener{ filterName, checked ->
            if (checked) {
                viewModel.addFilter(filterName.serverName, filterName.name)
            } else {
                viewModel.deleteFilter(filterName.serverName, filterName.name)
            }

            adapter.notifyItemChanged(filterName)
        }

        binding.filterGroupRecycler.addItemMargins(10, 10)

        rangeFilterAdapter.setOnRangeChangedListener { key, values ->
            viewModel.addRangeFilter(key, values)
        }

        viewModel.filters.observe(viewLifecycleOwner) { filters ->
            adapter.swapData(filters)
        }

        viewModel.rangeFilters.observe(viewLifecycleOwner) { rangeFilters ->
            adapter.swapData(rangeFilters)
        }

        binding.filterGroupRecycler.layoutManager = GridLayoutManager(context, 2).apply {
            spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when(adapter.getItem(position)){
                        is String -> 2
                        is RangeFilter -> 2
                        is FilterName -> 1
                        else -> -1
                    }
                }
            }
        }

        binding.filterGroupRecycler.adapter = adapter

    }

}