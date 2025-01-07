package com.example.week8_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.week8_assignment.ui.NewsApp
import com.example.week8_assignment.ui.theme.Week8_assignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week8_assignmentTheme {
                NewsApp()
            }
        }
    }
}
