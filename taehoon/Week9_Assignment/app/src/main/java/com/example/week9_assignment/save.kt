/*
package com.example.week9_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week9_assignment.ui.theme.Week9_AssignmentTheme
import android.content.Context
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

// SharedPreferences Helper
class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(userList: List<Pair<String, Int>>) {
        val json = gson.toJson(userList)
        sharedPreferences.edit().putString("user_list", json).apply()
    }

    fun getUser(): List<Pair<String, Int>> {
        val json = sharedPreferences.getString("user_list", null) ?: return emptyList()
        val type = object : TypeToken<List<Pair<String, Int>>>() {}.type
        return gson.fromJson(json, type)
    }
}

// ViewModel
class UserViewModel(context: Context) : ViewModel() {
    private val sharedPreferencesHelper = SharedPreferencesHelper(context)

    private val _userList = mutableStateListOf<Pair<String, Int>>()
    val userList: List<Pair<String, Int>> = _userList

    fun loadUsers() {
        _userList.clear()
        _userList.addAll(sharedPreferencesHelper.getUser())
    }

    fun addUser(name: String, age: Int) {
        _userList.add(name to age)
        sharedPreferencesHelper.saveUser(_userList)
    }

    fun deleteUser(user: Pair<String, Int>) {
        _userList.remove(user)
        sharedPreferencesHelper.saveUser(_userList)
    }

    init {
        loadUsers()
    }
}

// ViewModelFactory to pass context to ViewModel
class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// MainActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week9_AssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UserListApp()
                }
            }
        }
    }
}

@Composable
fun UserListApp(userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(LocalContext.current))) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = { Text("이름을 입력하세요.") }

        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = age,
            onValueChange = { age = it },
            label = { Text("나이를 입력하세요.") }

        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (name.isNotEmpty() && age.isNotEmpty()) {
                    userViewModel.addUser(name, age.toInt())
                    name = ""
                    age = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("저장하기")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(userViewModel.userList) { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "이름: ${user.first}, 나이: ${user.second}")
                    IconButton(onClick = { userViewModel.deleteUser(user) }) {
                        Icon(Icons.Default.Delete, contentDescription = "삭제아이콘")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Week9_AssignmentTheme {
        UserListApp()
    }
}

 */