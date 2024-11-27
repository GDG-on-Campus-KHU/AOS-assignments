package com.example.week5_assignment
import com.example.week5_assignment.com.example.week5_assignment.TaskAdapter
import com.example.week5_assignment.com.example.week5_assignment.Task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import android.util.Log
class FrontendActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_manager)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        val btnRemoveChecked = findViewById<Button>(R.id.btnRemoveChecked)
        val etNewTask = findViewById<EditText>(R.id.etNewTask)

        loadTasks()

        taskAdapter = TaskAdapter(taskList) { task -> }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        btnAddTask.setOnClickListener {
            val taskName = etNewTask.text.toString()
            if (taskName.isNotBlank()) {
                val newTask = Task(taskName)
                taskAdapter.addTask(newTask)
                etNewTask.text.clear()
                saveTasks()
            }
        }

        btnRemoveChecked.setOnClickListener {
            val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this)
            alertDialog.setTitle("삭제 확인") // 제목
            alertDialog.setMessage("선택하신 항목을 삭제하시겠습니까?") // 메시지

            // "확인" 누를 경우
            alertDialog.setPositiveButton("확인") { dialogInterface, _ ->
                taskAdapter.removeCheckedTasks() // 체크 삭제
                saveTasks()
                dialogInterface.dismiss() // 알림 창 닫기
            }

            // "취소" 누를 경우
            alertDialog.setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.dismiss() // 알림 창 닫기
            }

            // 확인 창 띄우기
            val deleteDialog = alertDialog.create()
            deleteDialog.show()
        }
    }

    private fun saveTasks() {
        val sharedPreferences = getSharedPreferences("FrontendTasks", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val jsonArray = JSONArray()
        taskList.forEach { task ->
            val jsonObject = JSONObject()
            jsonObject.put("name", task.name)
            jsonObject.put("isChecked", task.isChecked)
            jsonArray.put(jsonObject)
        }

        val jsonString = jsonArray.toString()
        editor.putString("tasks", jsonString)
        editor.apply()

        Log.d("FrontendActivity", "Saved tasks to SharedPreferences: $jsonString")
    }

    private fun loadTasks() {
        val sharedPreferences = getSharedPreferences("FrontendTasks", Context.MODE_PRIVATE)
        val tasksJson = sharedPreferences.getString("tasks", null) ?: return

        try {
            val jsonArray = JSONArray(tasksJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val isChecked = jsonObject.getBoolean("isChecked")
                taskList.add(Task(name, isChecked))
            }

            Log.d("FrontendActivity", "Loaded tasks from SharedPreferences: $taskList")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("FrontendActivity", "Failed to load tasks: ${e.message}")
            taskList.clear()
        }
    }

    override fun onPause() {
        super.onPause()
        saveTasks()
        Log.d("FrontendActivity", "Tasks saved during onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        saveTasks()
        Log.d("FrontendActivity", "Tasks saved during onDestroy")
    }
}