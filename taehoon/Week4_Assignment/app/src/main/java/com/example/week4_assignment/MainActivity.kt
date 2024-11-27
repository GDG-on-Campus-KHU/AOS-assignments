package com.example.week4_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.week4_assignment.ui.theme.Week4_AssignmentTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week4_AssignmentTheme()
        }
    }
}

@Composable
fun Week4_AssignmentTheme() {
    val (name, setName) = remember { mutableStateOf("") }
    val (age, setAge) = remember { mutableStateOf("") }
    val (school, setSchool) = remember { mutableStateOf("") }
    val (nickname, setNickname) = remember { mutableStateOf("") }
    val (mbti, setMbti) = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.8f)
            .align(Alignment.Center)

        ) {
            // 이미지 (위 절반)
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), //기본 파일이 괜찮다
                contentDescription = "Image_android",
                modifier = Modifier.fillMaxWidth().height(300.dp), // 이미지 크기를 조정
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 300.dp) // 이미지와 안겹치도록위쪽 패딩
            ) {
                val label = listOf("이름", "나이", "학교", "별명", "mbti")
                val value = listOf(name, age, school, nickname, mbti)
                for (i in 0..4) {
                    MakeTextField(
                        label[i], value[i],
                        setFunc = when (i) {
                            0 -> setName
                            1 -> setAge
                            2 -> setSchool
                            3 -> setNickname
                            4 -> setMbti
                            else -> setName
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MakeTextField(label: String, value: String, setFunc: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextField(

            value = value,
            onValueChange = setFunc, // 이름 입력값 상태 변경
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("$label 을(를) 입력하세요") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Week4_AssignmentTheme()
}
