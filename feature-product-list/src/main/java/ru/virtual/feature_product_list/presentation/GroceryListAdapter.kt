package ru.virtual.feature_product_list.presentation

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.virtual.core_android.ui.BaseAdapter
import ru.virtual.core_android.ui.BasePagingAdapter
import ru.virtual.feature_product_list.databinding.ItemGroceryListBinding
import ru.virtual.feature_product_list.domain.entities.GroceryList

class GroceryListAdapter: BasePagingAdapter<GroceryList, ItemGroceryListBinding>(DIFF_CALLBACK, ItemGroceryListBinding::inflate) {
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GroceryList>() {
            override fun areItemsTheSame(oldItem: GroceryList, newItem: GroceryList): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GroceryList, newItem: GroceryList): Boolean =
                oldItem == newItem
        }
    }
}