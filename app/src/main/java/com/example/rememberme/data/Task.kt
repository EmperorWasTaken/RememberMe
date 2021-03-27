package com.example.rememberme.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(var title:String?, var checked:Boolean): Parcelable
