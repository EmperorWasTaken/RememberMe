package com.example.rememberme.lists

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rememberme.EXTRA_TASK_INFO
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ListTaskLayoutBinding
import com.example.rememberme.tasks.AddNewTaskActivity
import com.example.rememberme.tasks.TaskRecyclerAdapter

class TaskListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ListTaskLayoutBinding
    private lateinit var taskList: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListTaskLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedTaskLists = TaskListHolder.ClickedList

        if(receivedTaskLists != null){
            taskList = receivedTaskLists
            Log.i("Details view", receivedTaskLists.toString())
        } else {
            setResult(Activity.RESULT_CANCELED, Intent(EXTRA_TASK_INFO).apply {

            })

            finish()
        }
        binding.titleList.text = taskList.listTitle
        binding.taskList.layoutManager = LinearLayoutManager(this)
        binding.taskList.adapter = TaskRecyclerAdapter(taskList.tasks)

        TaskListsDepositoryManager.instance.onTask = {
            (binding.taskList.adapter as TaskRecyclerAdapter).updateTask(it)
        }

        binding.taskAddButton.setOnClickListener(){
            newTaskActivity()
        }

        //TaskListsDepositoryManager.instance.loadTaskLists()

    }

    private fun newTaskActivity(){
        startActivity(Intent(applicationContext, AddNewTaskActivity::class.java))
    }
}