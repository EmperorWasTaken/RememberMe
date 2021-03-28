package com.example.rememberme.lists

import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList

class TaskListsDepositoryManager {

    private lateinit var listCollection:MutableList<TaskList>


    var onChange:((List<TaskList>) -> Unit)? = null
    var onTaskList:((List<TaskList>) -> Unit)? = null
    var onTaskListUpdate:((taskList: TaskList) -> Unit)? = null
    var onTask:((List<Task>) -> Unit)? = null

    fun loadTaskLists(){
        listCollection = mutableListOf()

        onTaskList?.invoke(listCollection)
    }

    fun updateTaskLists(taskList:TaskList){
        onTaskListUpdate?.invoke(taskList)
    }

    fun addTaskList(taskList: TaskList){
        listCollection.add(taskList)
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