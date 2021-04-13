package com.example.rememberme

import android.net.Uri
import android.util.Log
import com.example.rememberme.data.TaskList
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class TaskFirebaseManager(
        private val taskCollection:MutableList<com.example.rememberme.data.Task>
) {

    private var database = FirebaseDatabase.getInstance().reference
    init {
        database.keepSynced(true)
    }

    fun retrieveTaskData(path: String, end: String): Task<DataSnapshot> {
        return database.child(path).child(FirebaseAuth.getInstance().currentUser.uid).child(end).get().addOnSuccessListener {
            if (it.exists()) {
                taskCollection.clear()
            }

            it.children.mapNotNullTo(taskCollection) {
                it.getValue(com.example.rememberme.data.Task::class.java)
            }

            Log.println(Log.VERBOSE, "FirebaseManager", "I just executed successfully")
        }
    }

    fun putTaskData(path: String, taskID: String, listsCollection: MutableList<com.example.rememberme.data.Task>) {

        database.child(path).child(FirebaseAuth.getInstance().currentUser.uid).child(taskID).setValue(listsCollection).addOnSuccessListener {
            Log.println(Log.VERBOSE, "FirebaseManager", "I just executed successfully")
        }.addOnFailureListener {

        }
    }

    fun removeTaskData(path: String, position: Int){
        Log.d("task","Task..."+FirebaseAuth.getInstance().currentUser.uid+"..."+path+"..."+position)
        database.child("Tasks").child(FirebaseAuth.getInstance().currentUser.uid).child(path).child(position.toString()).removeValue()
    }



    companion object{
        //val instance = FirebaseManager()
    }

}
