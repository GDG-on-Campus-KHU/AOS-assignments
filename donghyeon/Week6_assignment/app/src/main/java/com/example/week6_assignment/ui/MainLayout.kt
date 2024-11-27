package com.example.week6_assignment.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.week6_assignment.ListViewModel

@Composable
fun MainLayout(navController: NavController, viewModel: ListViewModel) {
    val partList = listOf("DevRel", "FRONTEND", "BACKEND", "AI", "MOBILE")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val height = (LocalConfiguration.current.screenHeightDp - 60) / partList.size
        partList.forEach {
            ListRow(navController, it, height, viewModel)
        }
    }
}

@Composable
fun ListRow(navController: NavController, part: String, height: Int, viewModel: ListViewModel) {
    val backStackEntry = navController.currentBackStackEntry

    LaunchedEffect(backStackEntry) {
        if (backStackEntry?.destination?.route == "MainLayout") {
            viewModel.refreshListBoolean(part)
        }
    }

    Row(
        modifier = Modifier
            .height(height.dp)
            .fillMaxWidth()
            .padding(5.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
            .padding(10.dp)
            .clickable { navController.navigate("PartLayout/${part}") },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = part,
            style = TextStyle(fontSize = 15.sp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    MainLayout(rememberNavController(), ListViewModel())
}