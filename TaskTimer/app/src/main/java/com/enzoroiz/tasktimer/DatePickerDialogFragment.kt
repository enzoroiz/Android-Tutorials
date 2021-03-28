package com.enzoroiz.tasktimer

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatDialogFragment
import java.lang.IllegalStateException
import java.util.*

const val DATE_PICKER_DIALOG_ID = "DATE_PICKER_ID"
const val DATE_PICKER_DIALOG_TITLE = "DATE_PICKER_TITLE"
const val DATE_PICKER_DIALOG_DATE = "DATE_PICKER_DATE"
const val DATE_PICKER_DIALOG_FIRST_DAY_OF_WEEK = "DATE_PICKER_FIRST_DAY_OF_WEEK"

class DatePickerDialogFragment : AppCompatDialogFragment(), DatePickerDialog.OnDateSetListener {
    private var dialogId = 0
    private var listener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var title: String? = null
        val calendar = GregorianCalendar()
        var firstDayOfWeek = calendar.firstDayOfWeek

        arguments?.let {
            dialogId = it.getInt(DATE_PICKER_DIALOG_ID)
            title = it.getString(DATE_PICKER_DIALOG_TITLE)

            if (it.getInt(DATE_PICKER_DIALOG_FIRST_DAY_OF_WEEK) > 0) {
                firstDayOfWeek = it.getInt(DATE_PICKER_DIALOG_FIRST_DAY_OF_WEEK)
            }

            (it.getSerializable(DATE_PICKER_DIALOG_DATE) as? Date)?.let { date ->
                calendar.time = date
            }
        }

        val year = calendar.get(GregorianCalendar.YEAR)
        val month = calendar.get(GregorianCalendar.MONTH)
        val day = calendar.get(GregorianCalendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day).apply {
            setTitle(title)
            datePicker.firstDayOfWeek = firstDayOfWeek
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is DatePickerDialog.OnDateSetListener) {
            listener = context
        } else {
            throw IllegalStateException("Host must implement DatePickerDialog.OnDateSetListener interface")
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        view.tag = dialogId
        listener?.onDateSet(view, year, month, dayOfMonth)
    }
}