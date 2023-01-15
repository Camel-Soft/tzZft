package com.camelsoft.tzzft.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

fun hideKeyboard(context: Context, view: View?) {
    view?.let {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Long.toDateTime(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy  HH:mm:ss", Locale.getDefault())
    return dateFormat.format(this)
}
