package com.example.rememberme.data

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rememberme.EXTRA_TASK_INFO
import com.example.rememberme.TaskHolder
import com.example.rememberme.databinding.ActivityTaskDetailsBinding

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailsBinding
    private lateinit var task:Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val receivedTasks = intent.getParcelableExtra<Task>(EXTRA_TASK_INFO)
        val receivedTasks = TaskHolder.ClickedTask

        if(receivedTasks != null){
            task = receivedTasks
            Log.i("Details view", receivedTasks.toString())
        } else{

            setResult(Activity.RESULT_CANCELED, Intent(EXTRA_TASK_INFO).apply {
                // Add info we want to return to main
            })

            finish()
        }

        binding.title.text = task.title

    }
}