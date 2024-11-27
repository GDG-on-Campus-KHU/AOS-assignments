package com.example.week6_assignment.ui

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week6_assignment.ListViewModel
import com.example.week6_assignment.R

@Composable
fun PartLayout(navController: NavController, part: String, viewModel: ListViewModel) {
    val todoList = remember {
        when (part) {
            "DevRel" -> viewModel.devrelList
            "FRONTEND" -> viewModel.frontendList
            "BACKEND" -> viewModel.backendList
            "AI" -> viewModel.aiList
            "MOBILE" -> viewModel.mobileList
            else -> mutableStateListOf()
        }
    }
    var text by remember { mutableStateOf("") }
    var isButton by remember { mutableStateOf(false) }
    val todoCheckedList = mutableListOf<Int>()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, top = 20.dp, end = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = part,
                modifier = Modifier.align(Alignment.TopCenter),
                style = TextStyle(fontSize = 30.sp),
            )

            IconButton(
                onClick = {
                    if (todoCheckedList.isNotEmpty())
                        deleteDialog(context) { onResult ->
                            if (onResult) {
                                todoList.removeAll { it.first }
                                todoCheckedList.clear()
                            }
                        }
                    else {
                        Toast.makeText(context, "선택한 항목이 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 20.dp, end = 10.dp, bottom = 10.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    Color(100, 60, 180, 255)
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                .padding(10.dp)
        ) {
            itemsIndexed(todoList) { index, todo ->
                Row(
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Checkbox(
                        checked = todo.first,
                        onCheckedChange = {
                            if (!todo.first)
                                todoCheckedList.add(index)
                            else
                                todoCheckedList.remove(index)
                            todoList[index] = todo.copy(!todo.first)
                        }
                    )
                    Text(
                        text = todo.second,
                        modifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                            .fillMaxSize()
                            .padding(10.dp)
                            .clickable { navController.navigate("DetailLayout/${todo.second}") },
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current

            BasicTextField(
                modifier = Modifier
                    .weight(1f)
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
                singleLine = true,
                decorationBox = {
                    if (text.isEmpty())
                        Text(
                            text = "내용을 입력하세요",
                            style = TextStyle(fontSize = 20.sp),
                            color = Color.Gray,
                        )
                    it()
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        todoList.add(Pair(false, text))
                        text = ""
                        isButton = false
                        keyboardController?.hide()
                    }
                )
            )

            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 5.dp),
                onClick = {
                    todoList.add(Pair(false, text))
                    text = ""
                    isButton = false
                    keyboardController?.hide()
                },
                enabled = isButton
            ) {
                Text(text = "추가")
            }
        }
    }
}

fun deleteDialog(context: Context, onResult: (Boolean) -> Unit) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("삭제 확인")
        .setMessage("선택하신 항목을 삭제 하시겠습니까?")
        .setPositiveButton("확인") { _, _ ->
            onResult(true)
        }
        .setNegativeButton("취소") { _, _ ->
            onResult(false)
        }
        .show()
}


@Preview(showBackground = true)
@Composable
fun PreviewPart() {
    PartLayout(rememberNavController(), "part", ListViewModel())
}