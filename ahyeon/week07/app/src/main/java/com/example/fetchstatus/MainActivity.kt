package com.example.fetchstatus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetchstatus.ui.theme.FetchStatusTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FetchStatusTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DataFetchScreen()
                }
            }
        }
    }
}

@Composable
fun DataFetchScreen() {
    var statusMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = statusMessage ?: "")
            Button(
                onClick = {
                    statusMessage = "로딩 중..."
                    CoroutineScope(Dispatchers.Main).launch {
                        val fetchedData = async { fetchDataDelay() }
                        statusMessage = fetchedData.await()
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("데이터 가져오기")
            }
        }
    }
}

suspend fun fetchDataDelay(): String {
    delay(2000)
    return "데이터 가져오기 성공"
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FetchStatusTheme {
        DataFetchScreen()
    }
}