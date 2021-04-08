package com.example.rememberme.lists

import android.util.Log
import com.example.rememberme.ListFirebaseManager
import com.example.rememberme.TaskFirebaseManager
import com.example.rememberme.TaskListHolder
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TaskListsDepositoryManager {

    private var listCollection:MutableList<TaskList>
    private var taskCollection:MutableList<Task>
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
                Log.println(Log.VERBOSE, "TaskListDepositoryManager", listCollection.toString())
                onTaskList?.invoke(listCollection)
            }
        }
    }

    fun loadTask(end: String){
        taskFirebaseManager.retrieveTaskData("Tasks",end).addOnCompleteListener {
            if (it.isComplete) {
                Log.println(Log.VERBOSE, "TaskListDepositoryManager", taskCollection.toString())
                onTaskL?.invoke(taskCollection)
            }
        }
    }

    fun updateTaskLists(taskList:TaskList){
        onTaskListUpdate?.invoke(taskList)
    }

    fun addTaskList(taskList: TaskList){
        listCollection.add(taskList)
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


    fun createTaskInList(taskList:TaskList?, task: Task){

        if (taskList != null){
            taskList.tasks.add(task)
            updateTasks(taskList.tasks)
            taskCollection.add(task)
            taskFirebaseManager.putTaskData("Tasks", taskList.listTitle, taskCollection)
            onTaskList?.invoke(listCollection)
            updateChange()
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

    fun updateTaskProgress(task: Task, progress: Boolean){
        task.onChecked = progress
        TaskListHolder.ClickedList?.let {updateTasks(it.tasks)}
        updateChangeTask()

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
        val instance = TaskListsDepositoryManager()
    }
}
