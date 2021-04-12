package com.example.rememberme.tasks

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.Task
import com.example.rememberme.databinding.ActivityAddNewTaskBinding
import com.example.rememberme.lists.TaskListsDepositoryManager

class AddNewTaskActivity(
    private val TaskListsDepositoryManager: TaskListsDepositoryManager,
    private val progressBar: ProgressBar
) : DialogFragment() {


    private lateinit var _binding: ActivityAddNewTaskBinding
    private val binding get() = _binding!!
    private var taskListsDepositoryManager = TaskListsDepositoryManager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityAddNewTaskBinding.inflate(layoutInflater)

        binding.newTaskTitleButton.setOnClickListener{
            newTask()
        }

        return binding.root
    }

    private fun newTask() {
        val taskTitle = binding.newTaskTitle.text.toString()

        if(taskTitle.isNotEmpty()){

            val task = Task(taskTitle, false)

            taskListsDepositoryManager.createTaskInList(TaskListHolder.ClickedList, task)

            TaskListsDepositoryManager.updateChange()
            TaskListsDepositoryManager.updateChangeTask()

            progressBar.max = progressBar.max +1


            dialog?.hide()
            dialog?.cancel()

        }
    }
}