package com.example.rememberme.lists

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rememberme.EXTRA_TASK_INFO
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ListTaskLayoutBinding
import com.example.rememberme.tasks.AddNewTaskActivity
import com.example.rememberme.tasks.TaskRecyclerAdapter
import kotlinx.android.synthetic.main.activity_task_list_details.*

class TaskHolder {
    companion object{
        var ClickedTask: Task? = null
        var position: Int? = null
    }
}

class TaskListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ListTaskLayoutBinding
    private lateinit var taskList: TaskList
    private lateinit var progressBar: ProgressBar
    private var position: Int = 0


    var progressStatus = 0
    var handler = Handler()

    private val taskListsDepositoryManager = TaskListsDepositoryManager()

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
        binding.titleList.text = taskList.listTitle
        binding.taskList.layoutManager = LinearLayoutManager(this)
        binding.taskList.adapter = TaskRecyclerAdapter(taskList.tasks, this)

        taskListsDepositoryManager.onTaskL = {
            (binding.taskList.adapter as TaskRecyclerAdapter).updateTask(it)
        }

        binding.taskAddButton.setOnClickListener(){
            newTaskActivity()
        }


    }
    override fun onResume() {
        super.onResume()
        taskListsDepositoryManager.loadTask(taskList.listTitle)
    }

    private fun newTaskActivity(){
        startActivity(Intent(applicationContext, AddNewTaskActivity::class.java))
    }

}