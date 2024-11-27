package com.example.week5_assignment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week5_assignment.com.example.week5_assignment.Task
import com.example.week5_assignment.com.example.week5_assignment.TaskAdapter
import android.content.Intent
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDevRel = findViewById<Button>(R.id.btnDevRel)
        val btnFrontend = findViewById<Button>(R.id.btnFrontend)
        val btnBackend = findViewById<Button>(R.id.btnBackend)
        val btnAI = findViewById<Button>(R.id.btnAI)
        val btnMobile = findViewById<Button>(R.id.btnMobile)

        btnDevRel.setOnClickListener {
            val intent = Intent(this, DevRelActivity::class.java)
            startActivity(intent)
        }

        btnFrontend.setOnClickListener {
            val intent = Intent(this, FrontendActivity::class.java)
            startActivity(intent)
        }

        btnBackend.setOnClickListener {
            val intent = Intent(this, BackendActivity::class.java)
            startActivity(intent)
        }

        btnAI.setOnClickListener {
            val intent = Intent(this, AIActivity::class.java)
            startActivity(intent)
        }

        btnMobile.setOnClickListener {
            val intent = Intent(this, MobileActivity::class.java)
            startActivity(intent)
        }
    }
}