package com.example.rememberme.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberme.databinding.TaskLayoutBinding

class TasksRecyclerAdapter(private val tasks:MutableList<Task>): RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding:TaskLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(task: Task){
            binding.title.text = task.title
            binding.content.text = task.content
        }
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val task = tasks[position]
        holder.bind(task)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    public fun updateTaskList(newTasks:List<Task>){
        tasks.clear()
        tasks.addAll(newTasks)
    }

}