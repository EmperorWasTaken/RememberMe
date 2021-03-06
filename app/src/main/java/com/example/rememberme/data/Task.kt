package com.example.rememberme.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(val taskTitle:String, var onChecked:Boolean=false): Parcelable {
    constructor() : this("" ,false)
}
