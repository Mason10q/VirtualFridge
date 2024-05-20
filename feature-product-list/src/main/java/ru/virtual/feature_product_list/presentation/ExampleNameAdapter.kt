package ru.virtual.feature_product_list.presentation

import android.view.View
import ru.virtual.core_android.ui.BaseAdapter
import ru.virtual.feature_product_list.databinding.ItemGroceryListNameBinding
import ru.virtual.feature_product_list.domain.entities.GroceryList

class ExampleNameAdapter: BaseAdapter<String, ItemGroceryListNameBinding>(ItemGroceryListNameBinding::inflate) {

    private var onItemClick: ((String) -> Unit)? = null

    override fun bindView(binding: ItemGroceryListNameBinding, item: String) {
        binding.root.text = item
    }

    override fun onClick(view: View, item: String, position: Int) {
        onItemClick?.invoke(item)
    }

    fun setOnItemClick(listener: (String) -> Unit) {
        onItemClick = listener
    }

}