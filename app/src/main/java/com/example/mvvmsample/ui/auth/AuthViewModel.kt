package com.example.mvvmsample.ui.auth

import androidx.lifecycle.ViewModel
import com.example.mvvmsample.data.db.entities.User
import com.example.mvvmsample.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {


    fun getLoggedInUser() = userRepository.getUser()

    suspend fun userLogin(email: String, password: String) =
        withContext(Dispatchers.IO) { userRepository.userLogin(email, password) }


    suspend fun saveLoggedInUser(user: User) = userRepository.saveUser(user)

    suspend fun userSignUp(name: String, email: String, password: String) =
        withContext(Dispatchers.IO) { userRepository.userSignUp(name, email, password) }

}