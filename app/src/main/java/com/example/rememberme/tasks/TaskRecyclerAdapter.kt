package com.example.rememberme.tasks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
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
import kotlinx.android.synthetic.main.activity_task_details.view.*

class TaskRecyclerAdapter (private var tasks:MutableList<Task>, private var contexts: Context, private val listName: String, private val listId: Int, private val progressBar: ProgressBar) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {


    class ViewHolder(val binding: ActivityTaskDetailsBinding, val progressBar: ProgressBar, val tasks:MutableList<Task>):RecyclerView.ViewHolder(binding.root){
        val checkedTask = binding.taskCheckBox

        fun bind(task: Task, position: Int, contexts: Context){
            binding.titleTask.text = task.taskTitle
            binding.taskCheckBox.isChecked = task.onChecked

            binding.taskCard.radius = 0F



        }
    }

    override fun getItemCount(): Int = tasks.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val task = tasks[position]
        val context = contexts
        holder.bind(task, position, context)


        holder.binding.deleteTaskButton.setOnClickListener{
            TaskListsDepositoryManager.instance.removeTaskInList(TaskListHolder.ClickedList, task, position)
            Toast.makeText(contexts, "Task deleted", Toast.LENGTH_LONG).show()
            progressBar.max = progressBar.max -1
            progressBar.progress = progressBar.progress -1
            //contexts.startActivity(Intent(contexts, TaskListDetailsActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            //(contexts as Activity).finish()
            tasks?.removeAt(position)
            notifyItemRemoved(holder.adapterPosition)

        }

        holder.binding.taskCheckBox.setOnClickListener{
            progressBar.max = itemCount
            if (it?.taskCheckBox?.isChecked == true) {
                progressBar.progress = progressBar.progress + 1
            } else {
                progressBar.progress = progressBar.progress - 1
            }

            val dbCheck = FirebaseDatabase.getInstance().reference

            tasks[position].onChecked = holder.checkedTask.isChecked

            dbCheck.child("Tasks").child(FirebaseAuth.getInstance().currentUser.uid).child(listName).setValue(tasks).addOnCompleteListener{
                if (it.isSuccessful){
                    println("Successfully Changed Checkbox")
                } else {
                    println("Checkbox Not Changed")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivityTaskDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false), progressBar, tasks as MutableList<Task>
        )
    }

    fun updateTask(newTask:List<Task>){
        tasks = newTask as MutableList<Task>
        notifyDataSetChanged()
    }
}