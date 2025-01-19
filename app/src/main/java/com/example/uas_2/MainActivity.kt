package com.example.uas_2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uas_2.firebase.LoginViewModel
import com.example.uas_2.navigation.HelperBar
import com.example.uas_2.navigation.Screen
import com.example.uas_2.ui.presentation.AdminScreen
import com.example.uas_2.ui.presentation.RegisterScreen
import com.example.uas_2.ui.theme.UAS_2Theme
import com.google.firebase.FirebaseApp

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val loginViewModel : LoginViewModel = hiltViewModel()
            val state = loginViewModel.state.collectAsState(initial = null)
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
                },
                topBar = {
                    AnimatedVisibility(visible = currentRoute.HelperBar()) {
                        TopAppBar(
                            title = {
                                when (currentRoute){
                                    Screen.ScreenAdmin.route -> Text(
                                        text = "List Obat Kesehatan",
                                        fontSize = 14.sp
                                    )
                                }
                            },
                            modifier = Modifier
                                .shadow(4.dp),
                            actions = {
                                IconButton(onClick = {
                                    loginViewModel.logoutUser { success ->
                                        if (success) {
                                            Toast.makeText(applicationContext,"Logout Berhasil !", Toast.LENGTH_SHORT).show()
                                            navController.navigate(Screen.Login.route){
                                                popUpTo(Screen.Login.route){
                                                    inclusive = true
                                                }
                                            }
                                        } else {
                                            Toast.makeText(applicationContext, "Logout error", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = ""
                                    )
                                }
                            }

                        )
                    }
                },

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

