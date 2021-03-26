package com.example.rememberme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskDepositoryManager
import com.example.rememberme.data.TaskDetailsActivity
import com.example.rememberme.data.TasksRecyclerAdapter
import com.example.rememberme.databinding.ActivityMainBinding

const val EXTRA_TASK_INFO: String = "com.example.rememberme.task.info"
const val REQUEST_TASK_DETAILS:Int = 564567

class TaskHolder {
    companion object{
        var ClickedTask:Task? = null
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.taskListing.layoutManager = LinearLayoutManager(this)
        binding.taskListing.adapter = TasksRecyclerAdapter(emptyList<Task>(), this::onTaskClicked)

        TaskDepositoryManager.instance.onTasks = {
            (binding.taskListing.adapter as TasksRecyclerAdapter).updateTaskList(it)
        }

        TaskDepositoryManager.instance.loadTasks()

        binding.taskListAddButton.setOnClickListener {
            

        }

        // Later we can update the list with this
        //taskAdapter.updateTaskList(listOf(Task))

    }

    private fun addTask(title: String, content: String){
        val task = Task(title, content)
        TaskDepositoryManager.instance.addTask(task)
    }

    private fun onTaskClicked(task: Task): Unit {

        TaskHolder.ClickedTask = task

        val intent = Intent(this, TaskDetailsActivity::class.java).apply{
            putExtra(EXTRA_TASK_INFO, task)
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