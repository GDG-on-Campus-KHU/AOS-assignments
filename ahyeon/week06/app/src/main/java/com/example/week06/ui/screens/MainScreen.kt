package com.example.week06.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("web") }, modifier = Modifier.padding(8.dp)) {
            Text(text = "웹 바로가기")
        }
        Button(onClick = { navController.navigate("mobile") }, modifier = Modifier.padding(8.dp)) {
            Text(text = "모바일 바로가기")
        }
        Button(onClick = { navController.navigate("ai") }, modifier = Modifier.padding(8.dp)) {
            Text(text = "AI 바로가기")
        }
    }
}
