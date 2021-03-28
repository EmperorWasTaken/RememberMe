package com.example.rememberme.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ActivityTaskDetailsBinding
import com.example.rememberme.lists.TaskListsRecyclerAdapter

class TaskRecyclerAdapter (private var tasks:List<Task>) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {


    class ViewHolder(val binding: ActivityTaskDetailsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(task: Task){
            binding.titleTask.text = task.taskTitle

            binding.taskCard.radius = 0F

        }
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskRecyclerAdapter.ViewHolder, position: Int) {

        val task = tasks[position]
        holder.bind(task)

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