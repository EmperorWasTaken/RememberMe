package com.example.rememberme.data

class ListDepositoryManager {

    private lateinit var taskList:MutableList<List>

    var onTasks:((MutableList<List>)->Unit)? = null
    var onTasksUpdate:((list:List)->Unit)? = null

    fun loadLists(){
        taskList = mutableListOf(
            List("Store"),
            List("Mall"),
            List("Groceries")
        )

        onTasks?.invoke(taskList)
    }

    fun updateTasks(list:List){
        //Find task in list and replace with new task
        onTasksUpdate?.invoke(list)

    }

    fun addList(list:List){
        taskList.add()
        onTasks?.invoke(taskList)
    }

    companion object{
        val instance = TaskDepositoryManager()
    }

}