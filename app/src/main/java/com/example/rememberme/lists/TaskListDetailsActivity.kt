package com.example.rememberme.lists

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rememberme.EXTRA_TASK_INFO
import com.example.rememberme.MainActivity
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ListTaskLayoutBinding
import com.example.rememberme.tasks.AddNewTaskActivity
import com.example.rememberme.tasks.TaskRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_task_list_details.*
import java.util.Locale.filter
import java.util.logging.Level.WARNING


class TaskListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ListTaskLayoutBinding
    private lateinit var taskList: TaskList
    private var position: Int = 0

    private lateinit var taskListsDepositoryManager: TaskListsDepositoryManager

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

        taskListsDepositoryManager = TaskListsDepositoryManager()
        taskListsDepositoryManager.loadTask(taskList.listTitle, binding.listViewProgressBar)

        Log.println(Log.WARN, "Progress", taskListsDepositoryManager.calculateProgress().toString())

        //binding.listViewProgressBar.max = taskListsDepositoryManager.calculateProgress()!!


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

        TaskListsDepositoryManager.instance.onTaskL= {
            (binding.taskList.adapter as TaskRecyclerAdapter).updateTask(it)
            //TaskListsDepositoryManager.instance.calculateProgress()
            binding.listViewProgressBar.max = taskListsDepositoryManager.taskCollection.count()
            //println(taskListsDepositoryManager.taskCollection.count())
            //Log.println(Log.WARN, "Progress", taskListsDepositoryManager.taskCollection.count().toString())
            //binding.listViewProgressBar.progress = 2

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