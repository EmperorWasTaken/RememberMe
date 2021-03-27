package com.example.rememberme.data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rememberme.databinding.ActivityListDetailsBinding
import com.example.rememberme.databinding.ActivityTaskDetailsBinding


class ListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListDetailsBinding
    private lateinit var list:List

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}