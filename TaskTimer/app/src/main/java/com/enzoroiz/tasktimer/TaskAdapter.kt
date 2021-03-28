package com.enzoroiz.tasktimer

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.enzoroiz.tasktimer.TasksContract.Columns.ID
import com.enzoroiz.tasktimer.TasksContract.Columns.TASK_DESCRIPTION
import com.enzoroiz.tasktimer.TasksContract.Columns.TASK_NAME
import com.enzoroiz.tasktimer.TasksContract.Columns.TASK_SORT_ORDER
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_tasks_item.view.*
import java.lang.IllegalStateException

class TaskAdapter(private var cursor: Cursor?, private val listener: OnTaskInteraction) : Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_tasks_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        cursor?.let {
            if (it.count > 0) {
                if (it.moveToPosition(position).not()) {
                    throw IllegalStateException("Could not move cursor to position $position")
                }

                val task = Task(
                    name = it.getString(it.getColumnIndex(TASK_NAME)),
                    description = it.getString(it.getColumnIndex(TASK_DESCRIPTION)),
                    sortOrder = it.getInt(it.getColumnIndex(TASK_SORT_ORDER))
                ).apply { id = it.getLong(it.getColumnIndex(ID)) }

                holder.bind(task, listener)

                return
            }
        }

        val context = holder.containerView.context
        holder.containerView.txtTaskName.text = context.getString(R.string.empty_task_list_title)
        holder.containerView.txtTaskDescription.text = context.getString(R.string.empty_task_list_message)
        holder.containerView.btnTaskEdit.visibility = GONE
    }

    override fun getItemCount(): Int = cursor?.count ?: 1

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

class TaskViewHolder(override val containerView: View) : ViewHolder(containerView), LayoutContainer {
    lateinit var task: Task
        private set

    fun bind(task: Task, listener: OnTaskInteraction) {
        this.task = task
        containerView.apply {
            txtTaskName.text = task.name
            txtTaskDescription.text = task.description
            btnTaskEdit.visibility = VISIBLE

            btnTaskEdit.setOnClickListener {
                listener.onEdit(task)
            }

            setOnLongClickListener {
                listener.onLongClick(task)
                true
            }
        }
    }
}

interface OnTaskInteraction {
    fun onEdit(task: Task)
    fun onLongClick(task: Task)
}