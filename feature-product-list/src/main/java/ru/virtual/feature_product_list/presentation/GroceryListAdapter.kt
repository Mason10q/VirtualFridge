package ru.virtual.feature_product_list.presentation

import android.annotation.SuppressLint
import ru.virtual.core_android.ui.BaseAdapter
import ru.virtual.feature_product_list.databinding.ItemGroceryListBinding
import ru.virtual.feature_product_list.domain.entities.GroceryList

class GroceryListAdapter: BaseAdapter<GroceryList, ItemGroceryListBinding>(ItemGroceryListBinding::inflate) {
    @SuppressLint("SetTextI18n")
    override fun bindView(binding: ItemGroceryListBinding, item: GroceryList) {
        with(binding) {
            listName.text = item.name
            groceryProgressBar.setProgressPercentage(
                (item.productsMarked / item.productsAmount * 100).toDouble()
            )
            groceryProgressText.text = "${item.productsMarked}/${item.productsAmount}"
        }
    }
}