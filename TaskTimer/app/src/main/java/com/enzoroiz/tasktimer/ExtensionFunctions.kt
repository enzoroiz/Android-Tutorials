package com.enzoroiz.tasktimer

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.showDialog(id: Int, infoText: String, positiveText: String? = null, negativeText: String? = null) {
    val args = Bundle().apply {
        putInt(DIALOG_ID, id)
        putString(DIALOG_INFO_TEXT, infoText)
        putString(DIALOG_POSITIVE_TEXT, positiveText)
        putString(DIALOG_NEGATIVE_TEXT, negativeText)
    }

    AppDialog().apply {
        arguments = args
    }.show(supportFragmentManager, null)
}