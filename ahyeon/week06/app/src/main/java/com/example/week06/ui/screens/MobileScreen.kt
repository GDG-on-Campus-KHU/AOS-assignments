package com.example.week06.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week06.data.preferences.PreferencesHelper
import android.content.Context
@Composable
fun MobileScreen(navController: NavController) {

    val context = LocalContext.current
    var newTask by remember { mutableStateOf("") }
    var isDeleteMany by remember { mutableStateOf(false) }
    var willDeleteList by remember { mutableStateOf(listOf<Int>()) }
    var todoList by rememberSaveable { mutableStateOf(listOf<ListItem>()) }

    LaunchedEffect(Unit) {
        todoList = PreferencesHelper.getMobileTodoList(context)
    }

    LaunchedEffect(todoList) {
        PreferencesHelper.saveMobileTodoList(context, todoList)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
        }

        Text(text = "MOBILE", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.align(Alignment.TopCenter))

        if (isDeleteMany) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = {
                        isDeleteMany = false
                        willDeleteList = emptyList()
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Cancel Delete Many Mode")
                }

                IconButton(
                    onClick = {
                        todoList = todoList.filter { it.id !in willDeleteList }
                        willDeleteList = emptyList()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 40.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete All Checked")
                }
            }
        } else {
            IconButton(
                onClick = { isDeleteMany = true },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Enter Delete Many Mode")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BasicTextField(
                value = newTask,
                onValueChange = { newTask = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
            )

            IconButton(
                onClick = {
                        val newItem = ListItem(id = todoList.size, value = newTask)
                        todoList = todoList + newItem
                        newTask = ""
                },
                enabled = newTask.length > 4,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Task")
            }


            todoList.forEachIndexed { index, task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isDeleteMany) {
                        Checkbox(
                            checked = willDeleteList.contains(task.id),
                            onCheckedChange = { isChecked ->
                                todoList = todoList.toMutableList().apply {
                                    this[index] = this[index].copy(isChecked = isChecked)
                                }
                                willDeleteList = if (isChecked) {
                                    willDeleteList + task.id
                                } else {
                                    willDeleteList.filter { it != task.id }
                                }
                            }
                        )
                    }

                    Text(
                        text = task.value,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                todoList = todoList
                                    .toMutableList()
                                    .apply {
                                        this[index] = this[index].copy(isChecked = !task.isChecked)
                                    }
                            },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textDecoration = if (task.isChecked) TextDecoration.LineThrough else TextDecoration.None
                        )
                    )
                }
            }
        }
    }
}