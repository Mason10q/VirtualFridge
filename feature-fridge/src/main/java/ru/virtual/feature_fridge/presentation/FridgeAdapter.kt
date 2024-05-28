package ru.virtual.feature_fridge.presentation

import androidx.recyclerview.widget.DiffUtil
import ru.virtual.core_android.ui.BasePagingAdapter
import ru.virtual.feature_fridge.domain.entities.Product
import ru.virtual.feature_fridge.databinding.ItemProductBinding

class FridgeAdapter: BasePagingAdapter<Product, ItemProductBinding>(DIFF_CALLBACK, ItemProductBinding::inflate) {

    private var onRemoveBtnClick: ((Int, Int) -> Unit)? = null
    override fun bindView(binding: ItemProductBinding, item: Product, position: Int) {
        with(binding) {
            productName.text = item.name

            removeBtn.setOnClickListener{
                onRemoveBtnClick?.invoke(item.fridgeId, item.productId)
                notifyItemRemoved(position)
            }
        }
    }

    fun setOnRemoveBtnClick(listener: (Int, Int) -> Unit) {
        onRemoveBtnClick = listener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.productId== newItem.productId

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }


}