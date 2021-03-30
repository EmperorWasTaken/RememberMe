package com.example.rememberme.lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ActivityAddNewListBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Constants
import kotlinx.android.synthetic.main.list_task_layout.*

class AddNewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewListBinding
    private lateinit var database: FirebaseDatabase

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
            val progress = 0.0F
            val taskList = TaskList(listTitle, mutableList, progress)

            TaskListsDepositoryManager.instance.addTaskList(taskList)
            TaskListsDepositoryManager.instance.updateTaskLists(taskList)

            finish()
        }
    }
}