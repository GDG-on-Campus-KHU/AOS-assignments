package com.example.week5_assignment.ui.xml

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.runtime.mutableStateListOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week5_assignment.databinding.ActivityMainBinding

object Value {
    val todoList = mutableStateListOf<Pair<Boolean, String>>()
}

class TodoXml(context: Context) : FrameLayout(context) {

    private val binding: ActivityMainBinding =
        ActivityMainBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var adapter: TodoAdapter
    private val todoList = Value.todoList

    init {
        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(todoList) { position, isChecked ->
            todoList[position] = todoList[position].copy(first = isChecked)
            adapter.notifyItemChanged(position)
        }

        binding.todoRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.todoRecyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupListeners() {
        binding.addButton.setOnClickListener {
            val text = binding.todoInput.text.toString()
            if (text.isNotEmpty()) {
                todoList.add(Pair(false, text))
                adapter.notifyItemInserted(todoList.size - 1)
                binding.todoInput.text.clear()
            }
        }

        binding.clearButton.setOnClickListener {
            todoList.removeAll { it.first }
            adapter.notifyDataSetChanged()
        }
    }
}
