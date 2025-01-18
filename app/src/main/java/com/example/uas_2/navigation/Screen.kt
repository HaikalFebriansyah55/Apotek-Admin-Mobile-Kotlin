package com.example.uas_2.navigation

sealed class Screen(val route: String) {
    data object ScreenAdmin : Screen("Admin")
    data object RegisterScreen : Screen("Register")
}