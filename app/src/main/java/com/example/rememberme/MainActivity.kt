package com.example.rememberme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rememberme.data.*
import com.example.rememberme.databinding.ActivityMainBinding
import com.example.rememberme.lists.AddNewListActivity
import com.example.rememberme.lists.TaskListDetailsActivity
import com.example.rememberme.lists.TaskListsDepositoryManager
import com.example.rememberme.lists.TaskListsRecyclerAdapter

const val EXTRA_TASK_INFO: String = "com.example.rememberme.task.info"
const val REQUEST_TASK_DETAILS:Int = 564567

class TaskListHolder {
    companion object{
        var ClickedList:TaskList? = null
        var position: Int? = null
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val taskListsDepositoryManager = TaskListsDepositoryManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.taskListing.layoutManager = LinearLayoutManager(this)
        binding.taskListing.adapter = TaskListsRecyclerAdapter(emptyList<TaskList>(),this, this::onTaskListClicked)


        taskListsDepositoryManager.onTaskList = {
            (binding.taskListing.adapter as TaskListsRecyclerAdapter).updateTaskList(it)
        }

        binding.taskListAddButton.setOnClickListener {

            addTaskList()

        }

    }

    override fun onResume() {
        super.onResume()
        taskListsDepositoryManager.loadTaskLists()
    }

    private fun addTaskList(){

        startActivity(Intent(applicationContext, AddNewListActivity::class.java))
    }

    private fun onTaskListClicked(taskList: TaskList, position: Int): Unit {

        TaskListHolder.ClickedList = taskList
        TaskListHolder.position = position

        val intent = Intent(this, TaskListDetailsActivity::class.java).apply{
            putExtra(EXTRA_TASK_INFO, taskList)
        }

        startActivity(intent)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_TASK_DETAILS){

        }
    }
}