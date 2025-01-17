package com.example.week8_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week8_assignment.ui.theme.Week8_AssignmentTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week8_AssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel = NewsViewModel()
                    NewsScreen(viewModel)
                }
            }
        }
    }
}

data class NewsArticle(
    val title: String,
    val description: String?
)

data class NewsResponse(
    val articles: List<NewsArticle>
)

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String = "us"
    ): NewsResponse
}

class NewsViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsApiService = retrofit.create(NewsApiService::class.java)

    val articles = mutableStateOf<List<NewsArticle>>(emptyList())

    fun GetNews() {
        viewModelScope.launch(Dispatchers.IO) { //
            try {
                val response = newsApiService.getTopHeadlines("d5625a23b0d647b681aac61b6fad718e")
                articles.value = response.articles
            } catch (e: Exception) {
                articles.value = listOf(NewsArticle("Error", "Restart the app"))
            }
        }
    }
}


@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val articles = viewModel.articles.value

    LaunchedEffect(Unit) {
        viewModel.GetNews()
    }

    NewsList(articles)
}

@Composable
fun NewsList(articles: List<NewsArticle>) {
    LazyColumn {
        items(articles) { article ->
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = article.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.description ?: "Empty",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis // 뒤에 ...으로 표현
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    thickness = 5.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsAppPreview() {
    NewsList(articles = emptyList())
}
