package com.enzoroiz.tasktimer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enzoroiz.tasktimer.AppDialog.OnDialogInteraction
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.IllegalStateException

private const val DIALOG_DELETE_ID = 1
private const val DIALOG_TASK_ID = "task_id"
private const val DIALOG_DELETE_TASK_POSITION = "task_delete_position"

class MainActivityFragment : Fragment(), OnTaskInteraction, OnDialogInteraction {
    private var editClickedListener: OnEditClicked? = null
    private val adapter = TaskAdapter(null, this)
    private val viewModel by activityViewModels<TaskTimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.cursor.observe(this, Observer {
            adapter.swapCursor(it)?.close()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (direction == ItemTouchHelper.LEFT) {
                        val task = (viewHolder as TaskViewHolder).task
                        if (task.id != viewModel.taskBeingEditedId) {
                            onDelete(task, viewHolder.adapterPosition)
                        } else {
                            adapter.notifyItemChanged(viewHolder.adapterPosition)
                            Toast.makeText(requireContext(), getString(R.string.task_delete_being_edited_message), Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        )

        lstTasks.layoutManager = LinearLayoutManager(context)
        lstTasks.adapter = adapter
        itemTouchHelper.attachToRecyclerView(lstTasks)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditClicked) {
            editClickedListener = context
        } else {
            throw IllegalStateException("Host must implement OnEditClicked interface")
        }
    }

    override fun onDetach() {
        super.onDetach()
        editClickedListener = null
    }

    override fun onEdit(task: Task) {
        editClickedListener?.onEditClicked(task)
    }

    override fun onLongClick(task: Task) {
        viewModel.startTiming(task)
    }

    override fun onPositiveDialogResult(dialogId: Int, args: Bundle) {
        if (dialogId == DIALOG_DELETE_ID) {
            val taskId = args.getLong(DIALOG_TASK_ID)
            if (taskId != 0L) {
                viewModel.deleteTask(taskId)
            }
        }
    }

    override fun onNegativeDialogResult(dialogId: Int, args: Bundle) {
        restoreDeleteCanceledTask(dialogId, args)
    }

    override fun onDialogCanceled(dialogId: Int, args: Bundle) {
        restoreDeleteCanceledTask(dialogId, args)
    }

    fun onDelete(task: Task, position: Int) {
        val dialogArguments = Bundle().apply {
            putInt(DIALOG_ID, DIALOG_DELETE_ID)
            putLong(DIALOG_TASK_ID, task.id)
            putString(DIALOG_INFO_TEXT, requireContext().getString(R.string.task_delete_dialog_info_text, task.id, task.name))
            putString(DIALOG_POSITIVE_TEXT, requireContext().getString(R.string.task_delete_dialog_positive_button_text))
            putInt(DIALOG_DELETE_TASK_POSITION, position)
        }

        AppDialog().apply { arguments = dialogArguments }.show(childFragmentManager, null)
    }

    private fun restoreDeleteCanceledTask(dialogId: Int, args: Bundle) {
        if (dialogId == DIALOG_DELETE_ID) {
            val taskPosition = args.getInt(DIALOG_DELETE_TASK_POSITION)
            adapter.notifyItemChanged(taskPosition)
        }
    }

    interface OnEditClicked {
        fun onEditClicked(task: Task)
    }
}