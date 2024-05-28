package ru.virtual.feature_auth.presentation

import ru.virtual.core_android.ui.BaseAdapter
import ru.virtual.mylibrary.databinding.ItemSpaceBinding

class SpaceAdapter: BaseAdapter<Boolean, ItemSpaceBinding>(ItemSpaceBinding::inflate) {

    override fun bindView(binding: ItemSpaceBinding, item: Boolean, position: Int) {
        if(item) binding.root
    }
}