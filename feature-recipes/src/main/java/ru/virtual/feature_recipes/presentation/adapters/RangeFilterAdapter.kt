package ru.virtual.feature_recipes.presentation.adapters

import com.google.android.material.slider.RangeSlider
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import ru.virtual.feature_recipes.databinding.ItemRangeFilterBinding
import ru.virtual.feature_recipes.domain.entities.RangeFilter

class RangeFilterAdapter: ViewBindingDelegateAdapter<RangeFilter, ItemRangeFilterBinding>(ItemRangeFilterBinding::inflate) {

    private var onRangeChangedListener: (String, List<Float>) -> Unit = {_,_ ->}

    private var savedRanges: Map<String, List<Float>>? = null

    fun setOnRangeChangedListener(listener: (String, List<Float>) -> Unit) {
        onRangeChangedListener = listener
    }

    fun setSavedRanges(map: Map<String, List<Float>>) {
        savedRanges = map
    }

    override fun isForViewType(item: Any): Boolean  = item is RangeFilter

    override fun ItemRangeFilterBinding.onBind(item: RangeFilter) {
        with(this) {
            title.text = item.title
            slider.valueFrom = item.valueFrom
            slider.valueTo = item.valueTo
            slider.values = savedRanges?.get(item.serverName) ?: item.startValues
            slider.stepSize = item.stepSize

            this.slider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
                override fun onStartTrackingTouch(p0: RangeSlider) {}

                override fun onStopTrackingTouch(p0: RangeSlider) {
                    onRangeChangedListener(item.serverName, p0.values)
                }
            })
        }
    }


    override fun RangeFilter.getItemId(): Any = this.title

}