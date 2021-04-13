package com.example.rememberme.tasks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.Task
import com.example.rememberme.databinding.ActivityTaskDetailsBinding
import com.example.rememberme.DepositoryManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_task_details.view.*



class TaskRecyclerAdapter (private var tasks:MutableList<Task>, private var contexts: Context, private val listName: String, private val listId: Int, private val progressBar: ProgressBar) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {


    class ViewHolder(val binding: ActivityTaskDetailsBinding):RecyclerView.ViewHolder(binding.root){
        val checkedTask = binding.taskCheckBox

        fun bind(task: Task){
            binding.titleTask.text = task.taskTitle
            binding.taskCheckBox.isChecked = task.onChecked

            binding.taskCard.radius = 0F

        }
    }

    override fun getItemCount(): Int = tasks.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val task = tasks[position]
        val context = contexts
        holder.bind(task)


        holder.binding.deleteTaskButton.setOnClickListener{
            DepositoryManager.instance.removeTaskInList(TaskListHolder.ClickedList, task, position)
            Toast.makeText(contexts, "Task deleted", Toast.LENGTH_LONG).show()
            progressBar.max = progressBar.max -1
            progressBar.progress = progressBar.progress -1
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
        return ViewHolder(ActivityTaskDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun updateTask(newTask:List<Task>){
        tasks = newTask as MutableList<Task>
        notifyDataSetChanged()
    }
}