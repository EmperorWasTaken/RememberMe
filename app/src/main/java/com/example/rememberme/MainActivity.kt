package com.example.rememberme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rememberme.data.*
import com.example.rememberme.databinding.ActivityMainBinding
import com.example.rememberme.lists.AddNewListActivity
import com.example.rememberme.lists.TaskListDetailsActivity
import com.example.rememberme.lists.TaskListsRecyclerAdapter
import com.example.rememberme.userActivities.LoginActivity
import com.google.firebase.auth.FirebaseAuth

const val EXTRA_TASK_INFO: String = "com.example.rememberme.task.info"
const val REQUEST_TASK_DETAILS:Int = 564567

class TaskListHolder {
    companion object{
        var ClickedList:TaskList? = null
        var position: Int? = null
        var listName: String? = null
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val taskListsDepositoryManager = DepositoryManager()


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

        binding.logOutButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialogTitle)
            builder.setMessage(R.string.dialogMessage)

            builder.setPositiveButton("Yes"){dialogInterface, which ->
                Toast.makeText(applicationContext, "Logging out", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut();
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()


            }
            builder.setNegativeButton("No"){dialogInterface, which ->
                Toast.makeText(applicationContext, "Staying logged in", Toast.LENGTH_SHORT).show()
                finish()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }

    override fun onResume() {
        super.onResume()
        taskListsDepositoryManager.loadTaskLists()
    }

    private fun addTaskList(){

         AddNewListActivity(
            taskListsDepositoryManager
        ).show(
            supportFragmentManager, "ADD_NEW_LIST_FRAGMENT"
        )
    }

    private fun onTaskListClicked(taskList: TaskList, position: Int): Unit {

        TaskListHolder.ClickedList = taskList
        TaskListHolder.position = position
        TaskListHolder.listName = taskListsDepositoryManager.listCollection[position].listTitle

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