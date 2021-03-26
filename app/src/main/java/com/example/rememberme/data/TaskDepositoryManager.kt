package com.example.rememberme.data

class TaskDepositoryManager {

    private lateinit var taskList:MutableList<Task>

    var onTasks:((List<Task>)->Unit)? = null
    var onTasksUpdate:((task:Task)->Unit)? = null

    fun loadTasks(){
        taskList = mutableListOf(
            Task("Store", "Eggs"),
            Task("Store", "Bread"),
            Task("Christmas", "Lights")
        )

        onTasks?.invoke(taskList)
    }

    fun updateTasks(task:Task){
        //Find task in list and replace with new task
        onTasksUpdate?.invoke(task)

    }

    fun addTask(task:Task){
        taskList.add(task)
        onTasks?.invoke(taskList)
    }

    companion object{
        val instance = TaskDepositoryManager()
    }

}