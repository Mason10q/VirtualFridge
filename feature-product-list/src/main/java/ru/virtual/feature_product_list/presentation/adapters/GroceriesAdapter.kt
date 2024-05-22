package ru.virtual.feature_product_list.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.virtual.core_android.ui.BasePagingAdapter
import ru.virtual.feature_product_list.databinding.ItemGroceryBinding
import ru.virtual.feature_product_list.domain.entities.Grocery

class GroceriesAdapter: BasePagingAdapter<Grocery, ItemGroceryBinding>(DIFF_CALLBACK, ItemGroceryBinding::inflate) {

    private var onCheckBoxClick: ((Boolean, Int) -> Unit)? = null

    override fun bindView(binding: ItemGroceryBinding, item: Grocery, position: Int) {
        with(binding) {
            groceryName.text = item.name
            groceryAmount.text = item.amount.toString()
            groceryMark.isChecked = item.marked

            groceryMark.setOnClickListener{
                onCheckBoxClick?.invoke(binding.groceryMark.isChecked, item.productId)
            }
        }
    }

    fun setOnCheckBoxClick(listener: (Boolean, Int) -> Unit) {
        onCheckBoxClick = listener
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Grocery>() {
            override fun areItemsTheSame(oldItem: Grocery, newItem: Grocery): Boolean =
                oldItem.productId == newItem.productId

            override fun areContentsTheSame(oldItem: Grocery, newItem: Grocery): Boolean =
                oldItem == newItem
        }
    }

}