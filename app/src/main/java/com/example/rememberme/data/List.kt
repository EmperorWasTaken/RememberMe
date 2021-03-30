package com.example.rememberme.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskList(val listTitle:String, var tasks:MutableList<Task>, val progressList:Float): Parcelable {
    constructor() : this("", mutableListOf() ,0.0f)
}