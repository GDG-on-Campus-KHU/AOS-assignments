package com.example.week7_assignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    var text by remember { mutableStateOf("대기 중...") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    text = "작업 시작"
                    val result = fetchNetworkData()
                    text = result
                }
            }
        ) {
            Text("시작")
        }
    }
}

suspend fun fetchNetworkData(): String {
    return withContext(Dispatchers.IO) {
        try {
            // 네트워크 요청 예시
            delay(2000L)
            "네트워크 작업 완료"
        } catch (e: Exception) {
            Log.e("NetworkError", "네트워크 요청 실패", e)
            "네트워크 오류 발생"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
