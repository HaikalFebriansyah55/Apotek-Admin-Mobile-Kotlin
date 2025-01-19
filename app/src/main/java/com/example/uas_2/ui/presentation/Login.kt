package com.example.uas_2.ui.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.uas_2.firebase.LoginViewModel
import com.example.uas_2.R

import com.example.uas_2.navigation.Screen
import kotlinx.coroutines.launch



@Composable
fun Login(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState(initial = null)

    Box (
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                "Selamat Datang di PillPoint",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            Image(
                painter = painterResource(R.drawable.icon1),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            Text(
                "LOGIN",
                fontSize = 30.sp
            )
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ){
                Text("Email")
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text("Password")
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }
                        ) {
                            Icon(painter = if (isPasswordVisible){
                                painterResource(id = R.drawable.eyevisible)
                            }else{
                                painterResource(id = R.drawable.eyeblind)
                            }, contentDescription = "",
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ){

                Button(
                    onClick = {
                        navController.navigate(Screen.Register.route){
                            popUpTo(Screen.Login.route){
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier
                        .size(130.dp, 40.dp)
                ) {
                    Text("Register")
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (email.isBlank() || password.isBlank()) {
                                Toast.makeText(
                                    context,
                                    "email dan password harus diisi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                viewModel.loginUser(email, password) {
                                    if (it) {
                                        navController.navigate(Screen.ScreenAdmin.route) {
                                            popUpTo(Screen.Login.route) {
                                                inclusive = true
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context,"Email atau Password salah", Toast.LENGTH_SHORT).show()

                                    }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier
                        .size(130.dp, 40.dp)
                ) {
                    Text("Login")
                }
            }
        }
    }
}