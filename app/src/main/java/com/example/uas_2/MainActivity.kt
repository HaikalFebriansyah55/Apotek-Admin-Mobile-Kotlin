package com.example.uas_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uas_2.navigation.Screen
import com.example.uas_2.ui.presentation.AdminScreen
import com.example.uas_2.ui.presentation.RegisterScreen
import com.example.uas_2.ui.theme.UAS_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Scaffold(
                floatingActionButton = {
                    if (currentRoute == Screen.ScreenAdmin.route) {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate(Screen.RegisterScreen.route)
                            }
                        ) {
                            Text("+")
                        }
                    }
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.ScreenAdmin.route,
                    modifier = Modifier.padding(it)
                ) {
                    composable(Screen.ScreenAdmin.route) {
                        AdminScreen()
                    }
                    composable(Screen.RegisterScreen.route) {
                        RegisterScreen(navController = navController)
                    }
                }
            }
        }
    }
}
