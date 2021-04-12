package com.example.rememberme.tasks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ActivityTaskDetailsBinding
import com.example.rememberme.lists.TaskListDetailsActivity
import com.example.rememberme.lists.TaskListsDepositoryManager
import com.example.rememberme.lists.TaskListsRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class TaskRecyclerAdapter (private var tasks:List<Task>, private var contexts: Context, private val listName: String, private val listId: Int) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {


    class ViewHolder(val binding: ActivityTaskDetailsBinding):RecyclerView.ViewHolder(binding.root){
        val checkedTask = binding.taskCheckBox

        fun bind(task: Task, position: Int, contexts: Context){
            binding.titleTask.text = task.taskTitle
            binding.taskCheckBox.isChecked = task.onChecked

            binding.taskCard.radius = 0F

            binding.deleteTaskButton.setOnClickListener{
                TaskListsDepositoryManager.instance.removeTaskInList(TaskListHolder.ClickedList, task, position)
                Toast.makeText(contexts, "Task deleted", Toast.LENGTH_LONG).show()
                contexts.startActivity(Intent(contexts, TaskListDetailsActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                (contexts as Activity).finish()
            }
        }
    }

    override fun getItemCount(): Int = tasks.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val task = tasks[position]
        val context = contexts
        //val taskID = taskID
        holder.bind(task, position, context)


        holder.checkedTask.setOnCheckedChangeListener{ _, _ ->
            val dbCheck = FirebaseDatabase.getInstance().reference

            //val newCheckedTask = Task(task.taskTitle, holder.checkedTask.isChecked)
            tasks[position].onChecked = holder.checkedTask.isChecked

            dbCheck.child("Tasks").child(FirebaseAuth.getInstance().currentUser.uid).child(listName).setValue(tasks).addOnCompleteListener{
                if (it.isSuccessful){
                    println("Yey2")
                } else {
                    println("No2")
                }

            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivityTaskDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun updateTask(newTask:List<Task>){
        tasks = newTask
        notifyDataSetChanged()
    }
}