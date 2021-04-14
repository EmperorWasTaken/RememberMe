package com.example.rememberme.lists

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rememberme.DepositoryManager
import com.example.rememberme.EXTRA_TASK_INFO
import com.example.rememberme.MainActivity
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ListTaskLayoutBinding
import com.example.rememberme.tasks.AddNewTaskActivity
import com.example.rememberme.tasks.TaskRecyclerAdapter


class TaskListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ListTaskLayoutBinding
    private lateinit var taskList: TaskList
    private var position: Int = 0

    private lateinit var taskListsDepositoryManager: DepositoryManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListTaskLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedTaskLists = TaskListHolder.ClickedList
        val pos = TaskListHolder.position

        if(receivedTaskLists != null){
            taskList = receivedTaskLists
            if (pos != null) {
                position = pos
            }
            Log.i("Details view", receivedTaskLists.toString())
        } else {
            setResult(Activity.RESULT_CANCELED, Intent(EXTRA_TASK_INFO).apply {

            })

            finish()
        }

        taskListsDepositoryManager = DepositoryManager()
        taskListsDepositoryManager.loadTask(taskList.listTitle, binding.listViewProgressBar)

        binding.titleList.text = taskList.listTitle
        binding.taskList.layoutManager = LinearLayoutManager(this)
        binding.taskList.adapter = TaskRecyclerAdapter(taskListsDepositoryManager.taskCollection, this, TaskListHolder.listName!!, TaskListHolder.position!!, binding.listViewProgressBar)

        taskListsDepositoryManager.onTaskL = {
            (binding.taskList.adapter as TaskRecyclerAdapter).updateTask(it)
        }

        binding.taskAddButton.setOnClickListener(){
            newTaskActivity()
        }

        binding.goBackButton.setOnClickListener(){
            startActivity(Intent(applicationContext, MainActivity::class.java))
            
        }



        DepositoryManager.instance.onTaskL= {
            (binding.taskList.adapter as TaskRecyclerAdapter).updateTask(it)
            binding.listViewProgressBar.max = taskListsDepositoryManager.taskCollection.count()

        }

    }
    override fun onResume() {
        super.onResume()
        taskListsDepositoryManager.loadTask(taskList.listTitle, binding.listViewProgressBar)
    }

    private fun newTaskActivity(){
        AddNewTaskActivity(
            taskListsDepositoryManager, binding.listViewProgressBar
        ).show(
            supportFragmentManager, "TASK_LIST_FRAGMENT"
        )
        binding.taskList.adapter?.notifyDataSetChanged()

    }

}