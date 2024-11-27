package com.example.week5_assignment.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun StartLayout() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "파이팅",
            style = TextStyle(fontSize = 30.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStart() {
    StartLayout()
}