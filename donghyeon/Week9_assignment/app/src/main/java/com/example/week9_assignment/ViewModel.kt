//package com.example.week9_assignment
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
//class UserViewModel(private val userDao: UserDao) : ViewModel() {
//
//    val allUsers = userDao.getAllUsers()
//
//    fun  addUser(user: User) {
//        viewModelScope.launch {
//            userDao.insert(user)
//        }
//    }
//
//    fun deleteUser(user: User) {
//        viewModelScope.launch {
//            userDao.delete(user)
//        }
//    }
//}
