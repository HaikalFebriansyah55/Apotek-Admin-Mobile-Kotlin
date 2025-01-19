package com.example.uas_2.firebase
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser (email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser (email: String, password: String): Flow<Resource<AuthResult>>
    fun logoutUser(): Flow<Resource<Unit>>
    fun resetPassword(email: String): Flow<Resource<Unit>>
}