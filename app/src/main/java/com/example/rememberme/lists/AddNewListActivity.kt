package com.example.rememberme.lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ActivityAddNewListBinding
import kotlinx.android.synthetic.main.list_task_layout.*

class AddNewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newListTitleButton.setOnClickListener {
            newList()
        }


    }

    private fun newList() {
        var listTitle = binding.newListTitle.text.toString()

        if(listTitle.isNotEmpty()){

            val mutableList = mutableListOf<Task>()
            val taskList = TaskList(listTitle, mutableList)

            TaskListsDepositoryManager.instance.addTaskList(taskList)
            TaskListsDepositoryManager.instance.updateTaskLists(taskList)

            finish()
        }
    }
}