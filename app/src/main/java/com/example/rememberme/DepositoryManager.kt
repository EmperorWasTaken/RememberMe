package com.example.rememberme

import android.util.Log
import android.widget.ProgressBar
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList

class DepositoryManager {

    var listCollection:MutableList<TaskList> = mutableListOf()
    var taskCollection:MutableList<Task> = mutableListOf()
    private var taskID:String = ""
    private var firebaseManager: ListFirebaseManager
    private var taskFirebaseManager: TaskFirebaseManager

    var onChange:((List<TaskList>) -> Unit)? = null
    var onChangeL:((List<Task>) -> Unit)? = null
    var onTaskList:((MutableList<TaskList>) -> Unit)? = null
    var onTaskListUpdate:((taskList: TaskList) -> Unit)? = null
    var onTask:((List<Task>) -> Unit)? = null
    var onTaskL:((MutableList<Task>) -> Unit)? = null

    init {
        listCollection = mutableListOf()
        firebaseManager = ListFirebaseManager(listCollection)

        taskCollection = mutableListOf()
        taskFirebaseManager = TaskFirebaseManager(taskCollection)
    }

    fun loadTaskLists(){
        firebaseManager.retrieveListData("Lists").addOnCompleteListener {
            if (it.isComplete) {
                Log.println(Log.VERBOSE, "ListDepositoryManager", listCollection.toString())
                onTaskList?.invoke(listCollection)
            }
        }

    }

    fun loadTask(end: String, progressBar: ProgressBar){
        taskFirebaseManager.retrieveTaskData("Tasks",end).addOnCompleteListener {
            if (it.isComplete) {
                Log.println(Log.VERBOSE, "TaskDepositoryManager", taskCollection.toString())
                progressBar.max = it?.result?.children?.count()!!
                progressBar.progress = getAllCheckedTasks().count()!!
            }
        }
    }

    fun updateTaskLists(taskList:TaskList){
        onTaskListUpdate?.invoke(taskList)
    }

    fun addTaskList(taskList: TaskList){
        listCollection.add(taskList)

        Log.println(Log.VERBOSE, "ADDDING NEW DATA", listCollection.toString())

        firebaseManager.putListData("Lists", taskID, listCollection)

        onTaskList?.invoke(listCollection)
    }

    fun removeTaskList(taskList: TaskList?, position: Int){

        listCollection.remove(taskList)
        firebaseManager.removeListData("Lists/${taskID}", position)

        firebaseManager.retrieveListData("Lists").addOnCompleteListener {
            if (it.isComplete) {
                Log.println(Log.VERBOSE, "TaskListDepositoryManager", listCollection.toString())
                onTaskList?.invoke(listCollection)
            }
        }

        loadTaskLists()
        updateChange()
    }


    fun createTaskInList(taskList: TaskList?, task: Task){

        if (taskList != null){
            taskList.tasks.add(task)
            updateTasks(taskList.tasks)
            taskCollection.add(task)
            taskFirebaseManager.putTaskData("Tasks", taskList.listTitle, taskCollection)
            onTaskL?.invoke(taskCollection)
            onTask?.invoke(taskCollection)
            updateChange()
            updateChangeTask()
        }

    }

    fun removeTaskInList(taskList: TaskList?, task: Task, position: Int){

        if(taskList != null){
            taskList.tasks.remove(task)
            taskFirebaseManager.removeTaskData(taskList.listTitle, position)
            taskCollection.remove(task)
            updateTasks(taskList.tasks)
            updateChangeTask()
        }
    }

    fun getAllCheckedTasks(): List<Task> {
        return taskCollection.filter {
            it.onChecked}
    }

    fun updateTasks(tasks: List<Task>){
        onTask?.invoke(tasks)
    }

    fun updateChange(){
        onChange?.invoke(listCollection)
    }
    fun updateChangeTask(){
        onChangeL?.invoke(taskCollection)
    }

    companion object{
        val instance = DepositoryManager()
    }

}
