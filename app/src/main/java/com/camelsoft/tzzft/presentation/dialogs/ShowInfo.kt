package com.camelsoft.tzzft.presentation.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.camelsoft.tzzft.R

fun showInfo(context: Context, message: String, click: () -> Unit) {
    AlertDialog.Builder(context)
        .setIcon(R.drawable.img_info_48)
        .setTitle(R.string.attention)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton(R.string.ok) { dialog, id ->
            dialog.dismiss()
            click()
        }
        .create()
        .show()
}
