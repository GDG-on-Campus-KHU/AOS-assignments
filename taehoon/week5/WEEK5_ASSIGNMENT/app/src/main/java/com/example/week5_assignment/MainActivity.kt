package com.example.week5_assignment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week5_assignment.com.example.week5_assignment.Task
import com.example.week5_assignment.com.example.week5_assignment.TaskAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        val btnRemoveChecked = findViewById<Button>(R.id.btnRemoveChecked)
        val etNewTask = findViewById<EditText>(R.id.etNewTask)

        taskAdapter = TaskAdapter(taskList) { task ->
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        btnAddTask.setOnClickListener {
            val taskName = etNewTask.text.toString()
            if (taskName.isNotBlank()) {
                taskAdapter.addTask(Task(taskName))
                etNewTask.text.clear()
            }
        }

        btnRemoveChecked.setOnClickListener {
            taskAdapter.removeCheckedTasks()
        }
    }
}
