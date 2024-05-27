package ru.virtual.core_android.ui.utils

import android.widget.EditText

fun EditText.setTextAndSelection(text: CharSequence) {
    this.setText(text)
    this.setSelection(text.length)
}