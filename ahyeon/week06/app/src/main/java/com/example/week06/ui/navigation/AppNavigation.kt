package com.example.week06.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.week06.ui.screens.AiScreen
import com.example.week06.ui.screens.MainScreen
import com.example.week06.ui.screens.MobileScreen
import com.example.week06.ui.screens.WebScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("web") { WebScreen(navController) }
        composable("ai") { AiScreen() }
        composable("mobile"){ MobileScreen()}
    }
}