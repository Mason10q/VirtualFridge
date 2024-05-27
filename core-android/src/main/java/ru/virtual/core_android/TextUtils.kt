package ru.virtual.core_android

import android.util.Patterns.EMAIL_ADDRESS
import android.widget.EditText
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import ru.virtual.core_android.ui.SimpleTextWatcher


fun Long.toTimeFormat(): String = DateTimeFormatter.ofPattern("mm:ss")
    .withZone(ZoneId.of("UTC"))
    .format(Instant.ofEpochMilli(this))


fun CharSequence.isValidEmail() =
    this.isNotEmpty() && EMAIL_ADDRESS.matcher(this).matches()

fun EditText.addTextChangedListener(listener: SimpleTextWatcher) {
    this.addTextChangedListener(listener)
}