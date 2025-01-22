package com.example.week9_assignment.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week9_assignment.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)
    val coroutineScope = rememberCoroutineScope()
    var userName by remember { mutableStateOf("") }
    var userAge by remember { mutableStateOf("") }
    var userList by remember { mutableStateOf(listOf<Pair<String, Int>>()) }
    val selectedUsers = remember { mutableSetOf<Int>() }
    var isLoading by remember { mutableStateOf(false) }

    suspend fun fetchData() {
        userList = dataStoreManager.getAllUsersFlow().first()
        isLoading = false
    }

    fun saveUsers() {
        coroutineScope.launch {
            dataStoreManager.saveUserData(userName, userAge.toInt())
            userName = ""
            userAge = ""
        }
    }

    fun deleteSelectedUsers() {
        coroutineScope.launch {
            val updatedList = userList.filterIndexed { index, _ -> !selectedUsers.contains(index) }
            dataStoreManager.updateUserList(updatedList)
            selectedUsers.clear()
        }
    }

    LaunchedEffect(isLoading) { fetchData() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = {
                Text(
                    text = "이름을 입력하세요.",
                    style = TextStyle(color = Color.Gray)
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userAge,
            onValueChange = { userAge = it },
            label = {
                Text(
                    text = "나이를 입력하세요.",
                    style = TextStyle(color = Color.Gray)
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (userAge.toIntOrNull() != null) {
                isLoading = true
                saveUsers()
            } else {
                Toast.makeText(context, "나이에 숫자를 입력해주세요: $userAge", Toast.LENGTH_SHORT)
                    .show()
            }
        }) {
            Text("저장하기")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.TopCenter),
                text = "저장된 사용자 리스트",
                style = TextStyle(fontSize = 16.sp)
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp),
                onClick = {
                    isLoading = true
                    deleteSelectedUsers()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (isLoading)
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .align(Alignment.CenterHorizontally)
            )
        else {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(userList) { index, user ->
                    Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                        var isChecked by remember { mutableStateOf(false) }

                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                isChecked = !isChecked
                                if (isChecked)
                                    selectedUsers.add(index)
                                else
                                    selectedUsers.remove(index)
                            }
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .align(Alignment.CenterVertically),
                            text = "이름: ${user.first} ",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "나이: ${user.second}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
