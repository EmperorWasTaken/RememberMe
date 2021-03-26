package com.example.rememberme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rememberme.data.Task
import com.example.rememberme.data.TasksRecyclerAdapter
import com.example.rememberme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var taskAdapter:TasksRecyclerAdapter

    private var taskList:MutableList<Task> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.taskListing.layoutManager = LinearLayoutManager(this)
        taskAdapter = TasksRecyclerAdapter(taskList)
        binding.taskListing.adapter = taskAdapter

        // Later we can update the list with this
        //taskAdapter.updateTaskList(listOf(Task))


    }
}