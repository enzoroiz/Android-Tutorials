package com.enzoroiz.tasktimer

import android.content.Context
import android.database.Cursor
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_tasks_durations_item.view.*
import java.lang.IllegalStateException
import java.util.*

class TasksDurationsAdapter(context: Context, private var cursor: Cursor? = null) : Adapter<TasksDurationsViewHolder>() {
    private val dateFormat = DateFormat.getDateFormat(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksDurationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_tasks_durations_item, parent, false)
        return TasksDurationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksDurationsViewHolder, position: Int) {
        cursor?.let {
            if (it.count > 0) {
                if (it.moveToPosition(position).not()) {
                    throw IllegalStateException("Could not move cursor to position $position")
                }

                val taskName = it.getString(it.getColumnIndex(TasksDurationsContract.Columns.TASK_NAME))
                val taskDescription = it.getString(it.getColumnIndex(TasksDurationsContract.Columns.TASK_DESCRIPTION))
                val timingStartTime = it.getLong(it.getColumnIndex(TasksDurationsContract.Columns.TIMING_START_TIME))
                val timingDuration = it.getLong(it.getColumnIndex(TasksDurationsContract.Columns.TIMING_DURATION))
                val date = dateFormat.format(timingStartTime * 1000)
                val totalTime = formatDuration(timingDuration)

                holder.containerView.txtTaskName.text = taskName
                holder.containerView.txtTaskDescription?.text = taskDescription
                holder.containerView.txtTaskStartDate.text = date
                holder.containerView.txtTaskDuration.text = totalTime
            }
        }
    }

    override fun getItemCount(): Int = cursor?.count ?: 0

    private fun formatDuration(duration: Long): String {
        val hours = duration / 3600
        val remainder = duration - hours * 3600
        val minutes = remainder / 60
        val seconds = remainder % 60

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
    }

    // Should be called whenever the cursor that the adapter is using is changed
    fun swapCursor(newCursor: Cursor?): Cursor? {
        if (newCursor === cursor) return null

        val numItems = itemCount
        val oldCursor = cursor
        cursor = newCursor

        newCursor?.let {
            notifyDataSetChanged()
        } ?: notifyItemRangeRemoved(0, numItems)

        return oldCursor
    }
}

class TasksDurationsViewHolder(override val containerView: View) : ViewHolder(containerView), LayoutContainer