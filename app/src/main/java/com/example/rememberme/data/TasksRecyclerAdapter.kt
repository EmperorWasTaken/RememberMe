package com.example.rememberme.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberme.databinding.TaskLayoutBinding

class TasksRecyclerAdapter(private var tasks:List<Task>, private val onTaskClicked:(Task) -> Unit): RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding:TaskLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(task: Task, onTaskClicked:(Task) -> Unit){
            binding.titleTask.text = task.title
            binding.contentTask.text = task.content

            binding.taskCard.setOnClickListener {
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
        return ViewHolder(TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    public fun updateTaskList(newTasks:List<Task>){
        tasks = newTasks
        notifyDataSetChanged()
    }

}