package com.example.rememberme

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

class FirebaseManager {

    private val TAG:String = "RememberMe:FirebaseManager"



    fun upload(file: Uri){



        val ref = FirebaseStorage.getInstance().reference.child("lists/${file.lastPathSegment}")
        var uploadTask = ref.putFile(file)
        uploadTask.addOnSuccessListener {
            Log.d(TAG,"Upload Success ${it.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "Error saving file to Firebase", it)
        }

    }


    fun retrieveData(){


    }

    companion object{
        val instance = FirebaseManager()
    }

}
