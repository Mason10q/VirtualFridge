package ru.virtual.core_android.ui.utils

import androidx.recyclerview.widget.RecyclerView
import ru.virtual.core_android.ui.MarginItemDecorator

fun RecyclerView.addItemMargins(horizontal: Int = 0, vertical: Int = 0) {
    addItemDecoration(MarginItemDecorator(vertical, horizontal))
}