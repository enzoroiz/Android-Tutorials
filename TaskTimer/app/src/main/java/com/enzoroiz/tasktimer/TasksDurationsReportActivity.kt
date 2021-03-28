package com.enzoroiz.tasktimer

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tasks_durations.*
import java.lang.IllegalArgumentException
import java.util.*

private const val DIALOG_FILTER = 1
private const val DIALOG_DELETE = 2
private const val TIMINGS_DELETION_DATE = "Timings Deletion Date"

class TasksDurationsReportActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, AppDialog.OnDialogInteraction {
    private val adapter by lazy { TasksDurationsAdapter(this) }
    private val viewModel by viewModels<TasksDurationsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_durations_report)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.cursor.observe(this, Observer {
            adapter.swapCursor(it)?.close()
        })

        txtNameHeading.setOnClickListener { viewModel.sortOrder = SortColumn.NAME }
        txtDescriptionHeading?.setOnClickListener { viewModel.sortOrder = SortColumn.DESCRIPTION }
        txtStartDateHeading.setOnClickListener { viewModel.sortOrder = SortColumn.START_DATE }
        txtDurationHeading.setOnClickListener { viewModel.sortOrder = SortColumn.DURATION }

        listTasksDurations.layoutManager = LinearLayoutManager(this)
        listTasksDurations.adapter = adapter
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_tasks_durations_report_filter_period)?.let {
            if (viewModel.showWeeklyReport) {
                it.setIcon(R.drawable.ic_filter_seven)
                it.setTitle(R.string.menu_tasks_durations_report_filter_week)
            } else {
                it.setIcon(R.drawable.ic_filter_one)
                it.setTitle(R.string.menu_tasks_durations_report_filter_day)
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tasks_durations_report, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_tasks_durations_report_filter_period -> {
                viewModel.toggleReportFilterPeriod()
                invalidateOptionsMenu()
                return true
            }
            R.id.menu_tasks_durations_report_filter_date -> {
                showDatePickerDialog(getString(R.string.tasks_durations_report_dialog_filter_date_select), DIALOG_FILTER)
                return true
            }
            R.id.menu_tasks_durations_report_delete -> {
                showDatePickerDialog(getString(R.string.tasks_durations_report_dialog_delete_up_to_date_select), DIALOG_DELETE)
                return true
            }
            R.id.menu_tasks_durations_report_settings -> {
                SettingsDialog().show(supportFragmentManager, null)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        when (view?.tag) {
            DIALOG_FILTER -> viewModel.setReportFilterDate(year, month, dayOfMonth)
            DIALOG_DELETE -> {
                val calendar = GregorianCalendar().apply {
                    set(year, month, dayOfMonth, 0, 0, 0)
                }

                val upToDate = DateFormat.getDateFormat(this).format(calendar.time)
                val dialogArguments = Bundle().apply {
                    putInt(DIALOG_ID, DIALOG_DELETE)
                    putString(DIALOG_INFO_TEXT, getString(R.string.timings_delete_dialog_title, upToDate))
                    putLong(TIMINGS_DELETION_DATE, calendar.timeInMillis)
                }

                AppDialog().apply { arguments = dialogArguments }.show(supportFragmentManager, null)
            }
            else -> throw IllegalArgumentException("Invalid Dialog ID")
        }
    }

    private fun showDatePickerDialog(title: String, dialogId: Int) {
        val datePickerArguments = Bundle().apply {
            putString(DATE_PICKER_DIALOG_TITLE, title)
            putInt(DATE_PICKER_DIALOG_ID, dialogId)
            putInt(DATE_PICKER_DIALOG_FIRST_DAY_OF_WEEK, viewModel.firstDayOfWeek)
            putSerializable(DATE_PICKER_DIALOG_DATE, viewModel.getReportFilterDate())
        }

        DatePickerDialogFragment().apply {
            arguments = datePickerArguments
        }.show(supportFragmentManager, null)
    }

    override fun onPositiveDialogResult(dialogId: Int, args: Bundle) {
        when (dialogId) {
            DIALOG_DELETE -> viewModel.deleteTimings(args.getLong(TIMINGS_DELETION_DATE))
            else -> throw IllegalArgumentException("Invalid Dialog ID")
        }
    }
}