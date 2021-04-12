package com.example.rememberme.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskList(val listTitle:String, var tasks:MutableList<Task>, var progressList:Int): Parcelable {
    constructor() : this("", mutableListOf() ,0)
}