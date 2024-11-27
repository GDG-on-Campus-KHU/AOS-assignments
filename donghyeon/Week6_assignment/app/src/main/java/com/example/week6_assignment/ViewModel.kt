package com.example.week6_assignment

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
    val devrelList = mutableStateListOf<Pair<Boolean, String>>()
    val frontendList = mutableStateListOf<Pair<Boolean, String>>()
    val backendList = mutableStateListOf<Pair<Boolean, String>>()
    val aiList = mutableStateListOf<Pair<Boolean, String>>()
    val mobileList = mutableStateListOf<Pair<Boolean, String>>()

    fun refreshListBoolean(part: String) {
        when (part) {
            "DevRel" -> devrelList.replaceAll { it.copy(false) }
            "FRONTEND" -> frontendList.replaceAll { it.copy(false) }
            "BACKEND" -> backendList.replaceAll { it.copy(false) }
            "AI" -> aiList.replaceAll { it.copy(false) }
            "MOBILE" -> mobileList.replaceAll { it.copy(false) }
        }
    }
}