package com.enzoroiz.tasktimer

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.enzoroiz.tasktimer.debug.TestData
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_main.*

private const val DIALOG_CANCEL_EDIT_ID = 2

class MainActivity : AppCompatActivity(), AddEditFragment.OnSaveClicked,
    MainActivityFragment.OnEditClicked, AppDialog.OnDialogInteraction {
    private var isInTwoPaneMode = false
    private var aboutDialog: Dialog? = null
    private val viewModel by viewModels<TaskTimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        isInTwoPaneMode = resources.configuration.orientation == ORIENTATION_LANDSCAPE
        viewModel.timedTaskLiveData.observe(this, Observer<String> {
            txtCurrentTask.text = it?.let {
                getString(R.string.label_current_timed_task, it)
            } ?: getString(R.string.label_no_timed_task)
        })

        val fragmentAddEdit = supportFragmentManager.findFragmentById(R.id.fgt_add_edit_task)
        fragmentAddEdit?.let {
            showAddEditPane()
        } ?: let {
            fgt_main.visibility = VISIBLE
            fgt_add_edit_task.visibility = if (isInTwoPaneMode) INVISIBLE else GONE
        }
    }

    override fun onStop() {
        super.onStop()
        dismissAboutDialog()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        if (BuildConfig.DEBUG) menu.findItem(R.id.menu_main_action_generate)?.isVisible = true

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_action_generate -> TestData.generateData(contentResolver)
            R.id.menu_main_action_add_task -> taskAddEditRequest()
            R.id.menu_main_action_show_duration -> {
                startActivity(Intent(this, TasksDurationsReportActivity::class.java))
            }
            android.R.id.home -> {
                supportFragmentManager.findFragmentById(R.id.fgt_add_edit_task)?.let {
                    if (it is AddEditFragment && it.hasUnsavedChanges()) {
                        showDialog(
                            DIALOG_CANCEL_EDIT_ID,
                            getString(R.string.cancel_edit_dialog_info_text),
                            getString(R.string.cancel_edit_dialog_positive_button_text),
                            getString(R.string.cancel_edit_dialog_negative_button_text)
                        )
                    } else {
                        removeAddEditPane()
                    }
                }
            }
            R.id.menu_main_action_show_about -> showAboutDialog()
            R.id.menu_main_action_settings -> SettingsDialog().show(supportFragmentManager, null)
            else -> super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(R.id.fgt_add_edit_task)?.let {
            if (it is AddEditFragment && it.hasUnsavedChanges()) {
                showDialog(
                    DIALOG_CANCEL_EDIT_ID,
                    getString(R.string.cancel_edit_dialog_info_text),
                    getString(R.string.cancel_edit_dialog_positive_button_text),
                    getString(R.string.cancel_edit_dialog_negative_button_text)
                )
            } else {
                removeAddEditPane()
            }
        } ?: super.onBackPressed()
    }

    override fun onSaveClicked() {
        removeAddEditPane()
    }

    override fun onEditClicked(task: Task) {
        taskAddEditRequest(task)
    }

    override fun onPositiveDialogResult(dialogId: Int, args: Bundle) {
        if (dialogId == DIALOG_CANCEL_EDIT_ID) {
            removeAddEditPane()
        }
    }

    private fun taskAddEditRequest(task: Task? = null) {
        val fragment = AddEditFragment.newInstance(task)
        supportFragmentManager.beginTransaction().replace(R.id.fgt_add_edit_task, fragment).commit()

        showAddEditPane()
    }

    private fun showAddEditPane() {
        fgt_add_edit_task.visibility = VISIBLE
        fgt_main.visibility = if (isInTwoPaneMode) VISIBLE else GONE
    }

    private fun removeAddEditPane() {
        supportFragmentManager.findFragmentById(R.id.fgt_add_edit_task)?.let {
            supportFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }

        fgt_main.visibility = VISIBLE
        fgt_add_edit_task.visibility = if (isInTwoPaneMode) INVISIBLE else GONE

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun showAboutDialog() {
        val aboutLayout = layoutInflater.inflate(R.layout.about_dialog, null, false)
        aboutLayout.findViewById<TextView>(R.id.txtAboutAppVersion).text = BuildConfig.VERSION_NAME

        aboutDialog = AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton(R.string.ok) { _, _ -> dismissAboutDialog() }
            .setView(aboutLayout).create()?.apply {
                setCanceledOnTouchOutside(true)
            }

        aboutDialog?.show()
    }

    private fun dismissAboutDialog() {
        aboutDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}