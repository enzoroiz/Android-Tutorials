package com.enzoroiz.tasktimer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatDialogFragment
import kotlinx.android.synthetic.main.settings_dialog.*
import java.util.*

const val SHARED_PREFERENCES_REFERENCE = "app.shared_preferences"
const val SHARED_PREFERENCE_SETTINGS_FIRST_DAY = "settings.first_day"
const val SHARED_PREFERENCE_SETTINGS_IGNORE_TIMINGS_LESS_THAN = "settings.ignore_timings_less_than"
const val IGNORE_TIMINGS_LESS_THAN = 0

class SettingsDialog : AppCompatDialogFragment() {

    private val timingIntervals = intArrayOf(
        0,
        5,
        10,
        15,
        20,
        25,
        30,
        35,
        40,
        45,
        50,
        55,
        60,
        120,
        180,
        240,
        300,
        360,
        420,
        480,
        540,
        600,
        900,
        1800,
        2700
    )

    private val defaultFirstDayOfWeek = GregorianCalendar(Locale.getDefault()).firstDayOfWeek
    private var firstDayOfWeek = defaultFirstDayOfWeek
    private var ignoreTimingsLessThan = IGNORE_TIMINGS_LESS_THAN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.SettingsDialog)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.settings_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().setTitle(R.string.menu_main_action_settings)
        seekBarIgnoreTimings.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateIgnoreTimingsLessThanTitle(timingIntervals[progress])
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }
        })
        btnOkay.setOnClickListener {
            saveSettings()
            dismiss()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState ?: let {
            retrieveSettings()

            val progress = timingIntervals.binarySearch(ignoreTimingsLessThan)
            if (progress < 0) {
                throw IndexOutOfBoundsException("Value $ignoreTimingsLessThan not found in the timings interval array")
            }

            seekBarIgnoreTimings.max = timingIntervals.size - 1
            seekBarIgnoreTimings.progress = progress
            updateIgnoreTimingsLessThanTitle(ignoreTimingsLessThan)
            spnStartingDay.setSelection(firstDayOfWeek - GregorianCalendar.SUNDAY)
        }
    }

    private fun retrieveSettings() {
        with(requireContext().getSharedPreferences(SHARED_PREFERENCES_REFERENCE, Context.MODE_PRIVATE)) {
            firstDayOfWeek = getInt(SHARED_PREFERENCE_SETTINGS_FIRST_DAY, defaultFirstDayOfWeek)
            ignoreTimingsLessThan = getInt(
                SHARED_PREFERENCE_SETTINGS_IGNORE_TIMINGS_LESS_THAN,
                IGNORE_TIMINGS_LESS_THAN
            )
        }
    }

    private fun saveSettings() {
        val selectedFirstDayOfWeek = spnStartingDay.selectedItemPosition + GregorianCalendar.SUNDAY
        val selectedIgnoreTimingsLessThan = timingIntervals[seekBarIgnoreTimings.progress]

        with(requireContext().getSharedPreferences(SHARED_PREFERENCES_REFERENCE, Context.MODE_PRIVATE).edit()) {
            if (selectedFirstDayOfWeek != firstDayOfWeek) {
                putInt(SHARED_PREFERENCE_SETTINGS_FIRST_DAY, selectedFirstDayOfWeek)
            }

            if (selectedIgnoreTimingsLessThan != ignoreTimingsLessThan) {
                putInt(
                    SHARED_PREFERENCE_SETTINGS_IGNORE_TIMINGS_LESS_THAN,
                    selectedIgnoreTimingsLessThan
                )
            }

            apply()
        }
    }

    private fun updateIgnoreTimingsLessThanTitle(selectedTiming: Int) {
        if (selectedTiming < 60) {
            txtIgnoreTimings.text = getString(
                R.string.settings_dialog_ignore_seconds_title,
                selectedTiming,
                resources.getQuantityString(
                    R.plurals.settings_dialog_seconds,
                    selectedTiming
                )
            )
        } else {
            val minutes = selectedTiming / 60
            txtIgnoreTimings.text = getString(
                R.string.settings_dialog_ignore_seconds_title,
                minutes,
                resources.getQuantityString(
                    R.plurals.settings_dialog_minutes,
                    minutes
                )
            )
        }
    }
}