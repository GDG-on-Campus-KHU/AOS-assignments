package com.example.week6_assignment.ui

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DetailLayout(navController: NavController, text: String) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier.align(Alignment.Center),
                tint = Color.Black
            )
        }

        IconButton(
            onClick = { navController.popBackStack(route = "MainLayout", inclusive = false) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "",
                modifier = Modifier.align(Alignment.Center),
                tint = Color.Black
            )
        }

        Text(
            text = text,
            style = TextStyle(fontSize = 30.sp),
            modifier = Modifier.align(Alignment.Center)
        )

        Button(
            onClick = { search(context, text) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {
            Text(text = "찾기")
        }
    }
}

fun search(context: Context, query: String) {
    val googleSearchUrl = "https://www.google.com/search?q=$query"
    val naverSearchUrl = "https://search.naver.com/search.naver?where=nexearch&query=$query"

    val options = arrayOf("구글 검색", "네이버 검색")
    val builder = AlertDialog.Builder(context)
    builder.setTitle("검색 앱 선택")
        .setItems(options) { _, which ->
            when (which) {
                0 -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleSearchUrl))
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        downloadGoogleMapDialog(context)
                    }
                }

                1 -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(naverSearchUrl))
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        downloadNaverMapDialog(context)
                    }
                }
            }
        }
        .show()
}

private fun downloadGoogleMapDialog(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("구글 다운로드")
        .setMessage("구글이 설치되어 있지 않습니다.\n다운로드하시겠습니까?")
        .setPositiveButton("다운로드") { _, _ ->
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=com.google.android.googlequicksearchbox")
            )
            context.startActivity(intent)
        }
        .setNegativeButton("취소", null)
        .show()
}

private fun downloadNaverMapDialog(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("네이버 다운로드")
        .setMessage("네이버가 설치되어 있지 않습니다.\n다운로드하시겠습니까?")
        .setPositiveButton("다운로드") { _, _ ->
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=com.nhn.android.search")
            )
            context.startActivity(intent)
        }
        .setNegativeButton("취소", null)
        .show()
}

@Preview(showBackground = true)
@Composable
fun PreviewDetail() {
    DetailLayout(rememberNavController(), "text")
}