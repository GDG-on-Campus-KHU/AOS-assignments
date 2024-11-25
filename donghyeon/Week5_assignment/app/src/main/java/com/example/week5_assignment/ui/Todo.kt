package com.example.week5_assignment.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodoLayout() {
    val todoList = remember { mutableStateListOf<Pair<Boolean, String>>() }
    var text by remember { mutableStateOf("") }
    var isButton by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 10.dp)
        ) {
            Text(
                text = "할일 목록",
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(fontSize = 30.sp),
            )

            IconButton(
                onClick = { todoList.removeAll { it.first } },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 40.dp, end = 5.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    Color(100, 60, 180, 255)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(10.dp)
        ) {
            itemsIndexed(todoList) { index, todo ->
                Row(
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Checkbox(
                        checked = todo.first,
                        onCheckedChange = { todoList[index] = todo.copy(!todo.first) }
                    )
                    Text(
                        text = todo.second,
                        modifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                            .fillMaxSize()
                            .padding(10.dp),
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                    .padding(10.dp),
                value = text,
                onValueChange = {
                    if (it.length <= 10) {
                        text = it
                        isButton = it.isNotEmpty()
                    }
                },
                textStyle = TextStyle(fontSize = 20.sp),
                maxLines = 1,
                decorationBox = {
                    if (text.isEmpty())
                        Text(
                            text = "내용을 입력하세요",
                            style = TextStyle(fontSize = 20.sp),
                            color = Color.Gray,
                        )
                    it()
                },
            )

            Button(
                modifier = Modifier.padding(start = 5.dp),
                onClick = {
                    todoList.add(Pair(false, text))
                    text = ""
                    isButton = false
                },
                enabled = isButton
            ) {
                Text(text = "추가")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTodo() {
    TodoLayout()
}