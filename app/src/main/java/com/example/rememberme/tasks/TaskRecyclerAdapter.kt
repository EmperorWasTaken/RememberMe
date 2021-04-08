package com.example.rememberme.tasks

import android.app.Activity
import android.content.Context
import android.content.Intent
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

class TaskRecyclerAdapter (private var tasks:List<Task>, private var contexts: Context) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {


    class ViewHolder(val binding: ActivityTaskDetailsBinding):RecyclerView.ViewHolder(binding.root){
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

            binding.taskCheckBox.setOnClickListener{

            }

        }
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskRecyclerAdapter.ViewHolder, position: Int) {

        val task = tasks[position]
        val context = contexts
        holder.bind(task, position, context)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRecyclerAdapter.ViewHolder {
        return TaskRecyclerAdapter.ViewHolder(ActivityTaskDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    public fun updateTask(newTask:List<Task>){
        tasks = newTask
        notifyDataSetChanged()
    }
}