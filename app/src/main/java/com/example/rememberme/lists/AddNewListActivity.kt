package com.example.rememberme.lists

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.rememberme.DepositoryManager
import com.example.rememberme.R
import com.example.rememberme.data.Task
import com.example.rememberme.data.TaskList
import com.example.rememberme.databinding.ActivityAddNewListBinding

class AddNewListActivity(
    private val ListsDepositoryManager: DepositoryManager
) : DialogFragment() {

    private lateinit var _binding: ActivityAddNewListBinding
    private val binding get() = _binding!!
    private var listsDepositoryManager = ListsDepositoryManager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityAddNewListBinding.inflate(layoutInflater)

        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)

        binding.newListTitleButton.setOnClickListener {
            newList()
        }

        return binding.root
    }

    private fun newList() {
        var listTitle = binding.newListTitle.text.toString()

        if(listTitle.isNotEmpty()){

            val mutableList = mutableListOf<Task>()
            val progress = 0
            val taskList = TaskList(listTitle, mutableList, progress)

            listsDepositoryManager.addTaskList(taskList)

            dialog?.hide()
            dialog?.cancel()
        }
    }
}