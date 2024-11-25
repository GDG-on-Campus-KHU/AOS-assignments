package com.example.week6_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.week6_assignment.ui.DetailLayout
import com.example.week6_assignment.ui.MainLayout
import com.example.week6_assignment.ui.PartLayout
import com.example.week6_assignment.ui.theme.Week6_assignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ListViewModel = viewModel()
            Week6_assignmentTheme {
                LayoutNavigator(viewModel)
            }
        }
    }
}

@Composable
fun LayoutNavigator(viewModel: ListViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "MainLayout") {
        composable("MainLayout") {
            MainLayout(navController, viewModel)
        }
        composable(
            route = "PartLayout/{part}",
            arguments = listOf(navArgument("part") { type = NavType.StringType })
        ) {
            val part = it.arguments?.getString("part").toString()

            PartLayout(navController, part, viewModel)
        }
        composable(
            route = "DetailLayout/{text}",
            arguments = listOf(navArgument("text") { type = NavType.StringType })
        ) {
            val text = it.arguments?.getString("text").toString()

            DetailLayout(navController, text)
        }
    }
}