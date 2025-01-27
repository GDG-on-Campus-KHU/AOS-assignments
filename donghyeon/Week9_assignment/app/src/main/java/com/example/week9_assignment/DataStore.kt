package com.example.week9_assignment

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_data_store")

// Keys for storing multiple user data
private val USER_LIST_KEY = stringPreferencesKey("user_list")

class DataStoreManager(private val context: Context) {

    // Save a new user to the list
    suspend fun saveUserData(userName: String, userAge: Int) {
        context.dataStore.edit { preferences ->
            val currentList = preferences[USER_LIST_KEY]?.let { parseUserList(it) } ?: emptyList()
            val updatedList = currentList + Pair(userName, userAge)
            preferences[USER_LIST_KEY] = serializeUserList(updatedList)
        }
    }

    suspend fun updateUserList(updatedList: List<Pair<String, Int>>) {
        context.dataStore.edit { preferences ->
            preferences[USER_LIST_KEY] = serializeUserList(updatedList)
        }
    }

    // Retrieve all users as a Flow
    fun getAllUsersFlow(): Flow<List<Pair<String, Int>>> = context.dataStore.data
        .map { preferences ->
            preferences[USER_LIST_KEY]?.let { parseUserList(it) } ?: emptyList()
        }

    // Helper to parse the serialized user list
    private fun parseUserList(serializedList: String): List<Pair<String, Int>> {
        return serializedList.split("|").mapNotNull { entry ->
            val parts = entry.split(",")
            if (parts.size == 2) {
                val name = parts[0]
                val age = parts[1].toIntOrNull()
                if (age != null) Pair(name, age) else null
            } else null
        }
    }

    // Helper to serialize the user list
    private fun serializeUserList(userList: List<Pair<String, Int>>): String {
        return userList.joinToString("|") { "${it.first},${it.second}" }
    }
}
