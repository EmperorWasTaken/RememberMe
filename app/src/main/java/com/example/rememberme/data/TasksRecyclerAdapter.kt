package com.example.rememberme.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberme.databinding.TaskListLayoutBinding

class TasksRecyclerAdapter(private var tasks:MutableList<Task>, private val onTaskClicked:(Task) -> Unit): RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding:TaskListLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(task: Task, onTaskClicked:(Task) -> Unit){
            binding.titleList.text = task.title


            binding.listCard.setOnClickListener {
                onTaskClicked(task)
            }
        }
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TasksRecyclerAdapter.ViewHolder, position: Int) {

        val task = tasks[position]
        holder.bind(task, onTaskClicked)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TaskListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    public fun updateTaskList(newTasks:MutableList<Task>){
        tasks = newTasks
        notifyDataSetChanged()
    }

}