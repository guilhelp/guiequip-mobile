package com.example.guiequip.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.guiequip.ui.screens.HomeScreen
import com.example.guiequip.ui.screens.LoginScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(navController = navController) // Passa a função para a tela de login
        }

        composable("home_screen") {
            HomeScreen()
        }
    }
}

