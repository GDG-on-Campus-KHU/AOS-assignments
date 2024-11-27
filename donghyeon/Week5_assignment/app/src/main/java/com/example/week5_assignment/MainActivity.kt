package com.example.week5_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.week5_assignment.ui.TabLayout
import com.example.week5_assignment.ui.theme.Week5_assignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week5_assignmentTheme {
                TabLayout()
            }
        }
    }
}