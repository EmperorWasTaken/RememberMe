package com.example.rememberme.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.Task
import com.example.rememberme.databinding.ActivityAddNewListBinding
import com.example.rememberme.databinding.ActivityAddNewTaskBinding
import com.example.rememberme.lists.TaskListsDepositoryManager

class AddNewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newTaskTitleButton.setOnClickListener{
            newTask()
        }

    }

    private fun newTask() {
        val taskTitle = binding.newTaskTitle.text.toString()

        if(taskTitle.isNotEmpty()){

            val task = Task(taskTitle, false)

            TaskListsDepositoryManager.instance.createTaskInList(TaskListHolder.ClickedList, task)

            finish()
        }
    }
}