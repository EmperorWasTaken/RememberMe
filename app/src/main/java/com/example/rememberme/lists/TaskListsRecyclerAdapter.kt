package com.example.rememberme.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ActivityTaskListDetailsBinding
import com.example.rememberme.databinding.ListTaskLayoutBinding
import kotlinx.android.synthetic.main.activity_task_list_details.*

class TaskListsRecyclerAdapter(private var taskLists:List<TaskList>, private val onTaskListClicked:(TaskList) -> Unit): RecyclerView.Adapter<TaskListsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ActivityTaskListDetailsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(taskList: TaskList, onTaskListClicked:(TaskList) -> Unit){
            binding.titleTaskList.text = taskList.listTitle

            binding.taskCard.setOnClickListener {
                onTaskListClicked(taskList)
            }
        }
    }

    override fun getItemCount(): Int = taskLists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val taskList = taskLists[position]
        holder.bind(taskList, onTaskListClicked)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivityTaskListDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    public fun updateTaskList(newTaskLists:List<TaskList>){
        taskLists = newTaskLists
        notifyDataSetChanged()
    }

}