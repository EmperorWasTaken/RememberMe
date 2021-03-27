package com.example.rememberme.data

class TaskDepositoryManager {

    private lateinit var tasks:MutableList<Task>

    var onTasks:((MutableList<Task>)->Unit)? = null
    var onTasksUpdate:((task:Task)->Unit)? = null

    fun loadTasks(){
        tasks = mutableListOf(
            Task("Eggs", false),
            Task("Bacon", true),
            Task("Beans", false)
        )

        onTasks?.invoke(tasks)
    }

    fun updateTasks(task:Task){
        //Find task in list and replace with new task
        onTasksUpdate?.invoke(task)

    }

    fun addTask(task:Task){
        tasks.add(task)
        onTasks?.invoke(tasks)
    }

    companion object{
        val instance = TaskDepositoryManager()
    }

}