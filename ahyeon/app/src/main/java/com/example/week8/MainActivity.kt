package com.example.week8

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week8.ui.theme.Week8Theme
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL

data class NewsArticle(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("link") val link: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("pubDate") val pubDate: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week8Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsScreen()
                }
            }
        }
    }
}

@Composable
fun NewsScreen() {


    var newsList by remember { mutableStateOf<List<NewsArticle>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val fetchedNews = fetchNews()
            newsList = fetchedNews
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(newsList) { news ->
                NewsCard(news)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NewsCard(news: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            news.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = "${news.pubDate}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}


data class NewsApiResponse(
    val results: List<NewsArticle>
)

interface NewsApiService {
    @GET("latest")
    suspend fun fetchNews(
        @Query("apikey") apiKey: String = "pub_64586cec1994a26aaa9dfca6e32fbcec4ccfb",
        @Query("country") country: String = "kr"
    ): NewsApiResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://newsdata.io/api/1/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val instance: NewsApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(NewsApiService::class.java)
    }
}
suspend fun fetchNews(): List<NewsArticle> {
    return withContext(Dispatchers.IO) {
        try {
            val apiService = RetrofitClient.instance
            val articles = apiService.fetchNews(
                apiKey = "pub_64586cec1994a26aaa9dfca6e32fbcec4ccfb"
            ).results
            Log.d("News", articles.toString())
            articles
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Week8Theme {
        NewsScreen()
    }
}
