package com.example.rememberme.lists

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberme.DepositoryManager
import com.example.rememberme.MainActivity
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ActivityTaskListDetailsBinding


class TaskListsRecyclerAdapter(private var taskLists:List<TaskList>, private var contexts: Context, private val onTaskListClicked:(TaskList, Int) -> Unit): RecyclerView.Adapter<TaskListsRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ActivityTaskListDetailsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(taskList: TaskList, contexts: Context, position: Int,  onTaskListClicked:(TaskList, Int) -> Unit){
            binding.titleTaskList.text = taskList.listTitle

            binding.listCard.radius = 0F

            binding.listCard.setOnClickListener {
                onTaskListClicked(taskList, position)
            }

            binding.imageFrown.visibility = VISIBLE

            binding.imageSmile.visibility = INVISIBLE


            /*var complete = DepositoryManager.instance.getAllCheckedTasks()

            if ((complete) != 0){
                println("$complete = ${taskList.tasks.count()} and is loaded")

                binding.imageFrown.visibility = INVISIBLE

                binding.imageSmile.visibility = VISIBLE
            }*/

            binding.deleteListButton.setOnClickListener {
                DepositoryManager.instance.removeTaskList(taskList, position)
                Toast.makeText(contexts, "List deleted" , Toast.LENGTH_LONG).show()
                contexts.startActivity(Intent(contexts, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                (contexts as Activity).finish()
            }

        }

    }

    override fun getItemCount(): Int = taskLists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val taskList = taskLists[position]
        val context = contexts
        holder.bind(taskList, context, position, onTaskListClicked)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivityTaskListDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun updateTaskList(newTaskLists:List<TaskList>){
        taskLists = newTaskLists
        notifyDataSetChanged()
    }

}