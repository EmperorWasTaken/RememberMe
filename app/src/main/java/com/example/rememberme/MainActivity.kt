package com.example.rememberme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rememberme.data.*
import com.example.rememberme.databinding.ActivityMainBinding
import com.example.rememberme.lists.TaskListDetailsActivity
import com.example.rememberme.lists.TaskListsDepositoryManager
import com.example.rememberme.lists.TaskListsRecyclerAdapter
import kotlinx.android.synthetic.main.activity_task_details.*

const val EXTRA_TASK_INFO: String = "com.example.rememberme.task.info"
const val REQUEST_TASK_DETAILS:Int = 564567

class TaskHolder {
    companion object{
        var ClickedTask:Task? = null
    }
}

class TaskListHolder {
    companion object{
        var ClickedList:TaskList? = null
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.taskListing.layoutManager = LinearLayoutManager(this)
        binding.taskListing.adapter = TaskListsRecyclerAdapter(emptyList<TaskList>(), this::onTaskListClicked)

        TaskListsDepositoryManager.instance.onTaskList = {
            (binding.taskListing.adapter as TaskListsRecyclerAdapter).updateTaskList(it)
        }

        TaskListsDepositoryManager.instance.loadTaskLists()

        binding.taskListAddButton.setOnClickListener {

        }

        // Later we can update the list with this
        //taskAdapter.updateTaskList(listOf(Task))

    }

    private fun addTaskList(title: String, tasks:MutableList<Task>){
        val taskCollection = TaskList(title, tasks)
        TaskListsDepositoryManager.instance.addTaskList(taskCollection)
    }

    private fun onTaskListClicked(taskList: TaskList): Unit {

        TaskListHolder.ClickedList = taskList

        val intent = Intent(this, TaskListDetailsActivity::class.java).apply{
            putExtra(EXTRA_TASK_INFO, taskList)
        }

        startActivity(intent)
        //startActivityForResult(intent, REQUEST_TASK_DETAILS)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_TASK_DETAILS){

        }
    }
}