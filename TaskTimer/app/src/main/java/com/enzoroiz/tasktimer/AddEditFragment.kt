package com.enzoroiz.tasktimer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_add_edit_task.*
import java.lang.IllegalStateException

private const val ARG_TASK = "task"

class AddEditFragment : Fragment() {
    private var task: Task? = null
    private var listener: OnSaveClicked? = null
    private val viewModel by activityViewModels<TaskTimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        task = arguments?.getParcelable(ARG_TASK)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedInstanceState ?: let {
            task?.let {
                edtTaskName.setText(it.name)
                edtTaskDescription.setText(it.description)
                edtTaskSortOrder.setText(it.sortOrder.toString())

                viewModel.startEditingTask(it.id)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (listener is AppCompatActivity) {
            val actionBar = (listener as AppCompatActivity?)?.supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
        }

        btnSaveTask.setOnClickListener {
            saveTask()
            listener?.onSaveClicked()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSaveClicked) {
            listener = context
        } else {
            throw IllegalStateException("Host must implement OnSaveClicked interface")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        viewModel.stopEditingTask()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    private fun taskFromUi(): Task {
        val sortOrder =
            if (edtTaskSortOrder.text.isNotEmpty()) Integer.parseInt(edtTaskSortOrder.text.toString())
            else 0

        return Task(
            edtTaskName.text.toString(),
            edtTaskDescription.text.toString(),
            sortOrder
        ).apply { id = task?.id ?: 0L }
    }

    private fun saveTask() {
        val newTask = taskFromUi()
        if (newTask != task) {
            task = viewModel.saveTask(newTask)
        }
    }

    fun hasUnsavedChanges(): Boolean {
        val newTask = taskFromUi()
        return ((newTask != task) &&
                (newTask.name.isNotBlank()
                        || newTask.description.isNotBlank()
                        || newTask.sortOrder > 0)
                )
    }

    companion object {
        @JvmStatic
        fun newInstance(task: Task?) =
            AddEditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_TASK, task)
                }
            }
    }

    interface OnSaveClicked {
        fun onSaveClicked()
    }
}