package com.example.rememberme

import android.util.Log
import com.example.rememberme.data.TaskList
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ListFirebaseManager(
    private val listCollection:MutableList<TaskList>
) {

    private val TAG:String = "RememberMe:FirebaseManager"

    private var database = FirebaseDatabase.getInstance().reference
    private lateinit var refUsers: DatabaseReference
    private var fireBaseUserID:String = ""
    private var taskID:String = ""



    init {
        database.keepSynced(true)
    }

    fun retrieveListData(path: String): Task<DataSnapshot> {
        return database.child(path).child(FirebaseAuth.getInstance().currentUser.uid).get().addOnSuccessListener {
            if (it.exists()) {
                listCollection.clear()
            }

            it.children.mapNotNullTo(listCollection) {
                it.getValue<TaskList>(TaskList::class.java)
            }

            Log.println(Log.VERBOSE, "FirebaseManager", "I just executed successfully")
        }
    }

    fun putListData(path: String, taskID: String, listsCollection: MutableList<TaskList>) {

        database.child(path).child(FirebaseAuth.getInstance().currentUser.uid).setValue(listsCollection).addOnSuccessListener {
            Log.println(Log.VERBOSE, "FirebaseManager", "I just executed successfully")
        }.addOnFailureListener {

        }
    }

    fun removeListData(path: String, position: Int){
        database.child("Lists").child(FirebaseAuth.getInstance().currentUser.uid).child(position.toString()).removeValue()
    }


    companion object{
        //val instance = FirebaseManager()
    }

}
