package ru.virtual.feature_recipes.presentation.adapters

import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import ru.virtual.feature_recipes.databinding.ItemFilterTitleBinding

class FilterTitleAdapter :
    ViewBindingDelegateAdapter<String, ItemFilterTitleBinding>(ItemFilterTitleBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is String

    override fun ItemFilterTitleBinding.onBind(item: String) {
        this.filterType.text = item
    }

    override fun String.getItemId(): Any = this

}