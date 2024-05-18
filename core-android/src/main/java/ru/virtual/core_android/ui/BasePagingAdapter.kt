package ru.virtual.core_android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BasePagingAdapter<DATA : Any, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<DATA>,
    private val inflater: (LayoutInflater, ViewGroup, Boolean) -> VB
) : PagingDataAdapter<DATA, BasePagingAdapter.ViewHolder<DATA, VB>>(diffCallback) {


    private val callbacks = object : AdapterCallbacks<DATA, VB> {
        override fun bindViews(binding: VB, item: DATA) =
            bindView(binding, item)

        override fun onViewClicked(view: View, item: DATA, position: Int) =
            onClick(view, item, position)
    }

    abstract fun bindView(binding: VB, item: DATA)

    open fun onClick(view: View, item: DATA, position: Int) {}

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder<DATA, VB> =
        ViewHolder(
            inflater(LayoutInflater.from(parent.context), parent, false),
            callbacks
        )

    override fun onBindViewHolder(holder: ViewHolder<DATA, VB>, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }

    class ViewHolder<DATA : Any, VB : ViewBinding>(
        private val binding: VB,
        private val callbacks: AdapterCallbacks<DATA, VB>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DATA, position: Int) {
            callbacks.bindViews(binding, item)
            binding.root.setOnClickListener { callbacks.onViewClicked(binding.root, item, position) }
        }
    }
}