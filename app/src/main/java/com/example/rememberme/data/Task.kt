package com.example.rememberme.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(val title:String, val content:String): Parcelable
