package com.example.week4_assignment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityLayout()
        }
    }
}

@Composable
fun MainActivityLayout() {
    var isRotated by remember { mutableStateOf(true) }
    val rotation by animateFloatAsState(
        targetValue = if (isRotated) 0f else 180f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )
    val infoList = listOf("이름", "나이", "학교", "별명", "mbti")
    var infoTexts by remember { mutableStateOf(List(5) { "" }) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            onClick = {
                isRotated = !isRotated
            },
            modifier = Modifier
                .fillMaxSize(0.8f)
                .align(Alignment.Center)
                .shadow(
                    if (2f < rotation && rotation < 178f) 0.dp else 10.dp,
                    RoundedCornerShape(16.dp)
                )
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 16 * density
                }
        ) {
            if (rotation < 90f) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "클릭하여 자세히 보기",
                        color = Color.Gray,
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(Color.Blue)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize(0.7f)
                            .padding(top = 20.dp),
                    ) {
                        val height = LocalConfiguration.current.screenHeightDp / 20
                        infoList.forEachIndexed { index, info ->
                            InfoRow(info, height) { nextText ->
                                infoTexts = infoTexts.toMutableList().apply {
                                    this[index] = nextText
                                }
                            }
                        }
                    }

                    Button(
                        onClick = {
                            Toast.makeText(
                                context,
                                infoTexts.joinToString("\n"),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .weight(1f),
                    ) {
                        Text(text = "저장")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(infoValue: String, height: Int, infoText: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = infoValue,
            style = TextStyle(fontSize = 20.sp),
        )
        Spacer(
            modifier = Modifier
                .height(height.dp)
                .width(10.dp)
        )
        BasicTextField(
            value = text,
            onValueChange = {
                if (it.length <= 10) {
                    text = it
                    infoText(it)
                }
            },
            textStyle = TextStyle(fontSize = 18.sp),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = "${infoValue}을(를) 입력하세요.",
                        style = TextStyle(fontSize = 18.sp),
                        color = Color.Gray
                    )
                }
                innerTextField()
            },
            singleLine = true,
            maxLines = 1,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainActivityLayout()
}
