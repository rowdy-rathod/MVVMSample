package com.example.mvvmsample.ui.home

import androidx.lifecycle.ViewModel
import com.example.mvvmsample.data.repositories.UserRepository

class HomeViewModel(userRepository: UserRepository) : ViewModel() {
    val user = userRepository.getUser()
}