package com.example.week7_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.week7_assignment.ui.theme.Week7_AssignmentTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.async

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyScreen()
        }
    }
}
/*
1. Dispatcher
1) Dispatchers.main : 메인 스레드에서 해당 코루틴을 실행한다 UI 변경 등 일반적인
목적의 코루틴에 적합
2) Dispatchers.IO : 네트워크, 디스크 등 작업 수행하는 코루틴에 적합
3) Dispatchers.Default : 데이터 정렬 등 CPU 많이 사용할 때 적합


2. Corutine builder
 1) launch 2) withContext
 3) async : 하나의 코루틴 시작한 후 await()으로 결과를 기다린다. 현재의 스레드를
 중지시키지는 않는다.
  4) coroutineScope 5) supervisorScope 6) runBlocking
*/





@Composable
fun MyScreen() {
    var message by remember { mutableStateOf("Corutine Result : Idle") } // 초기값 설정

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, modifier = Modifier.padding(32.dp))
        Button(onClick = {
            message = "Corutine Result : Loading..."
            CoroutineScope(Dispatchers.Main).launch {
                val result = async { getData() }
                message = result.await() // 결과 대기 후 상태 업데이트
            }
        }) {
            Text("Start Courtine")
        }
    }
}

//suspend function : 일시 중단 함수
suspend fun getData(): String {
    delay(2000) // 2초 delay
    return "Courtine Result : Data fetched successfully"
}
