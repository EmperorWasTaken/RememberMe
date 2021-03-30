package com.example.rememberme

import android.net.Uri
import android.util.Log
import com.example.rememberme.data.TaskList
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class FirebaseManager(
    private val listCollection:MutableList<TaskList>
) {

    private val TAG:String = "RememberMe:FirebaseManager"

    private var database = FirebaseDatabase.getInstance().reference

    init {
        database.keepSynced(true)
    }

    fun retrieveData(path: String): Task<DataSnapshot> {
        return database.child(path).get().addOnSuccessListener {
            if (it.exists()) {
                listCollection.clear()
            }

            it.children.mapNotNullTo(listCollection) {
                it.getValue<TaskList>(TaskList::class.java)
            }

            Log.println(Log.VERBOSE, "FirebaseManager", "I just executed successfully")
        }
    }

    fun putData(path: String, listsCollection: MutableList<TaskList>) {
        database.child(path).setValue(listsCollection).addOnSuccessListener {
            Log.println(Log.VERBOSE, "FirebaseManager", "I just executed successfully")
        }.addOnFailureListener {

        }
    }

    companion object{
        //val instance = FirebaseManager()
    }

}
