package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import android.widget.Toast
import androidx.room.Room
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.room.Delete
import kotlinx.coroutines.launch
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseBuilder.getInstance(applicationContext)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserForm(database)
                }
            }
        }
    }
}

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: Int
)


@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
    @Delete
    suspend fun deleteUsers(users: List<User>)
}

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserForm(database: AppDatabase, modifier: Modifier = Modifier) {
    val userDao = database.userDao()
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var age by remember { mutableStateOf(TextFieldValue("")) }
    var userList by remember { mutableStateOf(listOf<User>()) }
    val selectedUsers = remember { mutableStateMapOf<Int, Boolean>() }

    LaunchedEffect(Unit) {
        userList = userDao.getAllUsers()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Name") }
        )

        TextField(
            value = age,
            onValueChange = { age = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Age") }
        )

        Button(
            onClick = {
                val ageInput = age.text
                val nameInput = name.text

                if (ageInput.toIntOrNull() == null) {
                    Toast.makeText(context, "나이는 숫자로 입력해주세요!", Toast.LENGTH_SHORT).show()
                } else {
                    val newUser = User(name = nameInput, age = ageInput.toInt())
                    coroutineScope.launch {
                        userDao.insertUser(newUser)
                        userList = userDao.getAllUsers()
                    }
                    name = TextFieldValue("")
                    age = TextFieldValue("")
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (userList.isNotEmpty()) {
            Column{
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            val usersToDelete = selectedUsers.filterValues { it }.keys
                            if (usersToDelete.isNotEmpty()) {
                                val usersToDeleteList = userList.filter { it.id in usersToDelete }
                                userDao.deleteUsers(usersToDeleteList)
                                userList = userDao.getAllUsers()
                                selectedUsers.clear()
                            } else {
                                Toast.makeText(context, "삭제할 사용자가 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Users")
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    userList.chunked(2).forEach { rowItems ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            rowItems.forEach { user ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = selectedUsers[user.id] ?: false,
                                        onCheckedChange = { isChecked ->
                                            selectedUsers[user.id] = isChecked
                                        }
                                    )
                                    Text("${user.name}(${user.age})")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

object DatabaseBuilder {
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (instance == null) {
            synchronized(AppDatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
            }
        }
        return instance!!
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val context = LocalContext.current
    val database = DatabaseBuilder.getInstance(context)

    MyApplicationTheme {
       UserForm(database)
    }
}