//package com.example.week9_assignment
//
//import android.content.Context
//import androidx.room.Dao
//import androidx.room.Database
//import androidx.room.Delete
//import androidx.room.Entity
//import androidx.room.Insert
//import androidx.room.PrimaryKey
//import androidx.room.Query
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import kotlinx.coroutines.flow.Flow
//
//@Entity(tableName = "user_table")
//data class User(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val name: String,
//    val age: Int
//)
//
//@Dao
//interface UserDao {
//    @Insert
//    suspend fun insert(user: User)
//
//    @Query("SELECT * FROM user_table")
//    fun getAllUsers(): Flow<List<User>>
//
//    @Delete
//    suspend fun delete(user: User)
//}
//
//@Database(entities = [User::class], version = 1, exportSchema = false)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "app_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
