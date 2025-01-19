package com.example.uas_2.firebase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    private val _state = Channel<LoginState>()
    private val firebaseAuth = FirebaseAuth.getInstance()
    val state = _state.receiveAsFlow()

    fun loginUser(email: String, password:String, callback: (Boolean) -> Unit){
        viewModelScope.launch {
            repository.loginUser(email = email, password = password).collect { result ->
                when (result){
                    is Resource.Error -> {
                        _state.send(LoginState(error = result.message)
                        )
                        callback(false)
                    }
                    is Resource.Loading -> {
                        _state.send(LoginState(loading = true)
                        )
                    }
                    is Resource.Success -> {
                        _state.send(LoginState(success = "Login Berhasil"))
                        callback(true)
                    }
                }
            }
        }
    }

    fun registerUser(email: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.registerUser(email = email, password = password).collect { result ->
                when (result){
                    is Resource.Error -> {
                        _state.send(LoginState(error = result.message))
                        callback(false)
                    }
                    is Resource.Loading -> {
                        _state.send(LoginState(loading = true))
                    }
                    is Resource.Success -> {
                        _state.send(LoginState(success = "Register Berhasil"))
                        callback(true)
                    }
                }
            }
        }
    }

    fun logoutUser(callback: (Boolean) -> Unit){
        viewModelScope.launch {
            repository.logoutUser().collect() { result ->
                when(result){
                    is Resource.Error -> {
                        Log.d("Logout", "Error : ${result.message}")
                        _state.send(LoginState(error = result.message)
                        )
                        callback(false)
                    }
                    is Resource.Loading -> {
                        Log.d("Logout", "Logging out...")
                        _state.send(LoginState(loading = true))
                    }
                    is Resource.Success -> {
                        Log.d("Logout", "Logout successful")
                        _state.send(LoginState(success = "Logout Berhasil")
                        )
                        callback(true)
                    }
                }
            }
        }
    }
    fun resetPassword(email: String, callback: (Boolean) -> Unit){
        viewModelScope.launch {
            repository.resetPassword(email).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.send(LoginState(error = result.message)
                        )
                        callback(false)
                    }
                    is Resource.Loading -> {
                        _state.send(LoginState(loading = true))
                    }
                    is Resource.Success -> {
                        _state.send(LoginState(success = "Email reset kata sandi telah dikirim")
                        )
                        callback(true)
                    }
                }
            }
        }
    }
}