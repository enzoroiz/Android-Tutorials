package com.enzoroiz.tasktimer

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import java.lang.IllegalStateException

const val DIALOG_ID = "id"
const val DIALOG_INFO_TEXT = "info_text"
const val DIALOG_POSITIVE_TEXT = "positive_text"
const val DIALOG_NEGATIVE_TEXT = "negative_text"

class AppDialog : AppCompatDialogFragment() {
    private var listener: OnDialogInteraction? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = when {
            parentFragment is OnDialogInteraction -> parentFragment as OnDialogInteraction
            context is OnDialogInteraction -> context
            else -> {
                throw IllegalStateException("Host must implement OnSaveClicked interface")
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments ?: throw IllegalArgumentException("Must provide DIALOG_ID and DIALOG_MESSAGE")

        val dialogId = args.getInt(DIALOG_ID).also {
            if (it == 0) throw IllegalArgumentException("Must provide DIALOG_ID")
        }

        val infoText = args.getString(DIALOG_INFO_TEXT) ?: let {
            throw IllegalArgumentException("Must provide DIALOG_MESSAGE")
        }

        val positiveText = args.getString(DIALOG_POSITIVE_TEXT)
            ?: requireContext().resources.getString(R.string.ok)

        val negativeText = args.getString(DIALOG_NEGATIVE_TEXT)
            ?: requireContext().resources.getString(R.string.cancel)

        return AlertDialog.Builder(requireContext())
            .setMessage(infoText)
            .setPositiveButton(positiveText) { _, _ ->
                listener?.onPositiveDialogResult(dialogId, args)
            }
            .setNegativeButton(negativeText) { _, _ ->
                listener?.onNegativeDialogResult(dialogId, args)
            }
            .create()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCancel(dialog: DialogInterface) {
        with(requireArguments()) {
            listener?.onDialogCanceled(this.getInt(DIALOG_ID), this)
        }
    }

    internal interface OnDialogInteraction {
        fun onPositiveDialogResult(dialogId: Int, args: Bundle) { }
        fun onNegativeDialogResult(dialogId: Int, args: Bundle) { }
        fun onDialogCanceled(dialogId: Int, args: Bundle) { }
    }
}