package ru.virtual.feature_product_list.presentation.adapters

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import ru.virtual.core_android.ui.BasePagingAdapter
import ru.virtual.feature_product_list.databinding.ItemAddGroceryBinding
import ru.virtual.feature_product_list.domain.entities.Grocery

class AddGroceriesAdapter: BasePagingAdapter<Grocery, ItemAddGroceryBinding>(DIFF_CALLBACK, ItemAddGroceryBinding::inflate) {

    private var onGroceryAddClick: ((Grocery) -> Unit)? = null
    private var onGroceryReduceClick: ((Grocery) -> Unit)? = null


    @SuppressLint("SetTextI18n")
    override fun bindView(binding: ItemAddGroceryBinding, item: Grocery, position: Int) {
        with(binding) {
            groceryName.text = item.name
            groceryAmount.text = item.amount.toString()

            groceryAmount.isVisible = item.amount > 0
            reduceBtn.isVisible = item.amount > 0
            binding.groceryAmount.text = item.amount.toString()


            addBtn.setOnClickListener{
                onGroceryAddClick?.invoke(item)
                getItem(position)?.let { it.amount += 1 }
                notifyItemChanged(position)
            }

            reduceBtn.setOnClickListener{
                onGroceryReduceClick?.invoke(item)
                getItem(position)?.let { it.amount -= 1 }
                notifyItemChanged(position)
            }
        }
    }

    fun setOnAddGroceryListener(listener: (Grocery) -> Unit) {
        onGroceryAddClick = listener
    }

    fun setOnGroceryReduceListener(listener: (Grocery) -> Unit) {
        onGroceryReduceClick = listener
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Grocery>() {
            override fun areItemsTheSame(oldItem: Grocery, newItem: Grocery): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Grocery, newItem: Grocery): Boolean =
                oldItem == newItem
        }
    }
}