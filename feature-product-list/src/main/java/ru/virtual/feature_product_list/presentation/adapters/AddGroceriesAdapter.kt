package ru.virtual.feature_product_list.presentation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import ru.virtual.core_android.ui.BasePagingAdapter
import ru.virtual.feature_product_list.databinding.ItemAddGroceryBinding
import ru.virtual.feature_product_list.databinding.ItemGroceryBinding
import ru.virtual.feature_product_list.domain.entities.Grocery

class AddGroceriesAdapter: BasePagingAdapter<Grocery, ItemAddGroceryBinding>(DIFF_CALLBACK, ItemAddGroceryBinding::inflate) {

    private var onGroceryAddClick: ((Int, Int) -> Unit)? = null
    private var onGroceryReduceClick: ((Int, Int) -> Unit)? = null


    @SuppressLint("SetTextI18n")
    override fun bindView(binding: ItemAddGroceryBinding, item: Grocery, position: Int) {
        with(binding) {
            groceryName.text = item.name
            groceryAmount.text = item.amount.toString()

            if(item.amount > 0) {
                groceryAmount.visibility = View.VISIBLE
                reduceBtn.visibility = View.VISIBLE
            }

            addBtn.setOnClickListener{
                onGroceryAddClick?.invoke(item.productId, item.amount)

                getItem(position)?.let {
                    it.amount += 1
                    binding.groceryAmount.text = it.amount.toString()
                }

                if(item.amount > 0) {
                    groceryAmount.visibility = View.VISIBLE
                    reduceBtn.visibility = View.VISIBLE
                    //addBtn.isSelected = true
                }

                notifyItemChanged(position)
            }

            reduceBtn.setOnClickListener{
                onGroceryReduceClick?.invoke(item.productId, item.amount)

                getItem(position)?.let {
                    it.amount -= 1
                    binding.groceryAmount.text = it.amount.toString()

                    if(it.amount == 0) {
                        reduceBtn.visibility = View.GONE
                        groceryAmount.visibility = View.GONE
                        //addBtn.isSelected = false
                    }
                }

                notifyItemChanged(position)
            }
        }
    }

    fun setOnAddGroceryListener(listener: (Int, Int) -> Unit) {
        onGroceryAddClick = listener
    }

    fun setOnGroceryReduceListener(listener: (Int, Int) -> Unit) {
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