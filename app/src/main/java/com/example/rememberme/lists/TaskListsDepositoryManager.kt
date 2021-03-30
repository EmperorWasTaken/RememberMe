package com.example.rememberme.lists

import android.util.Log
import com.example.rememberme.FirebaseManager
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList

class TaskListsDepositoryManager {

    private lateinit var listCollection:MutableList<TaskList>
    private lateinit var firebaseManager: FirebaseManager

    var onChange:((List<TaskList>) -> Unit)? = null
    var onTaskList:((MutableList<TaskList>) -> Unit)? = null
    var onTaskListUpdate:((taskList: TaskList) -> Unit)? = null
    var onTask:((List<Task>) -> Unit)? = null

    init {
        listCollection = mutableListOf()
        firebaseManager = FirebaseManager(listCollection)
    }

    fun loadTaskLists(){
        firebaseManager.retrieveData("Lists").addOnCompleteListener {
            if (it.isComplete) {
                Log.println(Log.VERBOSE, "TaskListDepositoryManager", listCollection.toString())
                onTaskList?.invoke(listCollection)
            }
        }
    }

    fun updateTaskLists(taskList:TaskList){
        onTaskListUpdate?.invoke(taskList)
    }

    fun addTaskList(taskList: TaskList){
        listCollection.add(taskList)
        firebaseManager.putData("Lists", listCollection)

        onTaskList?.invoke(listCollection)
    }

    fun removeTaskList(taskList: TaskList?){

        listCollection.remove(taskList)
        loadTaskLists()
        updateChange()
    }


    fun createTaskInList(taskList:TaskList?, task: Task){

        if (taskList != null){
            taskList.tasks.add(task)
            updateTasks(taskList.tasks)
            updateChange()


        }

    }

    fun removeTaskInList(taskList: TaskList?, task: Task){

        if(taskList != null){
            taskList.tasks.remove(task)
            updateTasks(taskList.tasks)
            updateChange()
        }
    }

    /*fun updateTaskInListProgress(task: Task, progress: Boolean){
        task.onChecked = progress
        TaskListHolder.ClickedList?.let {updateTasks(it.tasks)}
    }*/

    fun updateTasks(tasks: List<Task>){
        onTask?.invoke(tasks)
    }

    fun updateChange(){
        onChange?.invoke(listCollection)
    }

    companion object{
        val instance = TaskListsDepositoryManager()
    }
}