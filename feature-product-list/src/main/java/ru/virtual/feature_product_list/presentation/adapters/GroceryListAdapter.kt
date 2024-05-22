package ru.virtual.feature_product_list.presentation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import ru.virtual.core_android.ui.BasePagingAdapter
import ru.virtual.feature_product_list.databinding.ItemGroceryListBinding
import ru.virtual.feature_product_list.domain.PercentUtil
import ru.virtual.feature_product_list.domain.entities.GroceryList

class GroceryListAdapter: BasePagingAdapter<GroceryList, ItemGroceryListBinding>(DIFF_CALLBACK, ItemGroceryListBinding::inflate) {

    private var onItemClick: ((GroceryList) -> Unit)? = null
    private var onRedactButtonClick: ((GroceryList) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun bindView(binding: ItemGroceryListBinding, item: GroceryList, position: Int) {
        with(binding) {
            listName.text = item.name
            groceryProgressBar.setProgressPercentage(PercentUtil.getPercents(item.productsMarked, item.productsAmount))
            groceryProgressText.text = "${item.productsMarked}/${item.productsAmount}"
            redactBtn.setOnClickListener{ _ -> onRedactButtonClick?.invoke(item) }
        }
    }

    override fun onClick(view: View, item: GroceryList, position: Int) {
        onItemClick?.invoke(item)
    }

    fun setOnItemClick(listener: (GroceryList) -> Unit) {
        onItemClick = listener
    }

    fun setOnRedactButtonClick(listener: (GroceryList) -> Unit) {
        onRedactButtonClick = listener
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