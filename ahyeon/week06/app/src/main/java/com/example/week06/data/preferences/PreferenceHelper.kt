package com.example.week06.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.week06.ui.screens.ListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferencesHelper {
    private const val PREFS_NAME = "todo_prefs"
    private const val KEY_TODO_LIST = "web_todo_list"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveTodoList(context: Context, todoList: List<ListItem>) {
        val gson = Gson()
        val json = gson.toJson(todoList)
        getPreferences(context).edit().putString(KEY_TODO_LIST, json).apply()
    }

    fun getTodoList(context: Context): List<ListItem> {
        val gson = Gson()
        val json = getPreferences(context).getString(KEY_TODO_LIST, "")
        val type = object : TypeToken<List<ListItem>>() {}.type
        return if (json.isNullOrEmpty()) emptyList() else gson.fromJson(json, type)
    }
}
