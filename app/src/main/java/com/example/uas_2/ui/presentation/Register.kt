package com.example.uas_2.ui.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.uas_2.firebase.LoginViewModel
import com.example.uas_2.R
import com.example.uas_2.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun Register(
    navController: NavController,
    viewModel : LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
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
        ) {
            Spacer(modifier = Modifier.height(15.dp))
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
                "REGISTER",
                fontSize = 30.sp
            )
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
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
                            Icon(
                                painter = if (isPasswordVisible) {
                                    painterResource(id = R.drawable.eyevisible)
                                } else {
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
                Text("Confirm Password")
                OutlinedTextField(
                    value = confirmpassword,
                    onValueChange = { confirmpassword = it },
                    label = { Text("Confirm Password") },
                    singleLine = true,
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isConfirmPasswordVisible = !isConfirmPasswordVisible
                            }
                        ) {
                            Icon(
                                painter = if (isConfirmPasswordVisible) {
                                    painterResource(id = R.drawable.eyevisible)
                                } else {
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
            Button(
                onClick = {
                    coroutineScope.launch {
                        when {
                            email.isBlank() || password.isBlank() || confirmpassword.isBlank() -> {
                                Toast.makeText(context, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                            }
                            password.length < 7 -> {
                                Toast.makeText(context, "Kata sandi minimal 7 karakter", Toast.LENGTH_SHORT).show()

                            }
                            password != confirmpassword -> {
                                Toast.makeText(context, "Kata sandi tidak sama", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                viewModel.registerUser(email, password) {
                                    if (it) {
                                        Toast.makeText(context, "Daftar berhasil", Toast.LENGTH_SHORT).show()
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(Screen.Register.route){
                                                inclusive = true
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context, "Pendaftaran gagal, coba lagi", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                modifier = Modifier
                    .size(130.dp, 40.dp)
            ) {
                Text("Register")
            }
        }
    }
}
