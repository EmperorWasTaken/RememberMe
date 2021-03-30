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
    }
}

class TaskListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ListTaskLayoutBinding
    private lateinit var taskList: TaskList
    private lateinit var progressBar: ProgressBar

    var progressStatus = 0
    var handler = Handler()

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

        /*progressBarCompletion.progress = 0
        progressStatus = 0

        val tasksCompleted: Float = 0F*/

        /*Thread(Runnable {
            while (progressStatus < tasksCompleted){
                progressStatus +=1

                Thread.sleep(50)

                handler.post {
                    progressBar.progress = progressStatus

                    var percentage = ((progressStatus.toDouble() / 100))

                    TaskListsDepositoryManager.instance.updateTaskInListProgress(task, percentage)


                }
            }
        })*/

    }

    private fun newTaskActivity(){
        startActivity(Intent(applicationContext, AddNewTaskActivity::class.java))
    }

}