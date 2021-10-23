package com.example.mvvmsample.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvmsample.R
import com.example.mvvmsample.databinding.ActivitySignupBinding
import com.example.mvvmsample.util.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), KodeinAware {

    // make sure you have import => import org.kodein.di.android.kodein
    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        binding.buttonSignUp.setOnClickListener {
            onSignUp()
        }

        binding.textViewLogin.setOnClickListener {
            onBackPressed()
        }

    }

    private fun onSignUp() {
        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val confirmPassword = binding.editTextPasswordConfirm.text.toString().trim()

        if (name.isNullOrEmpty()) {
            binding.rootLayout.snackbar("Enter name")
            return
        }

        if (email.isNullOrEmpty()) {
            binding.rootLayout.snackbar("Enter email")
            return
        }

        if (password.isNullOrEmpty()) {
            binding.rootLayout.snackbar("Enter password")
            return
        }

        if (confirmPassword.isNullOrEmpty()) {
            binding.rootLayout.snackbar("Enter conform password")
            return
        }

        if (password != confirmPassword) {
            binding.rootLayout.snackbar("Password does not match")
            return
        }

        lifecycleScope.launch {
            try {
                binding.progressBar.show()
                val response = viewModel.userSignUp(name!!, email!!, password!!)
                if (response.isSuccessful!!) {
                    binding.rootLayout.snackbar(response.message!!)
                    if (!response.user_exist!!) {
                        binding.editTextName.setText("")
                        binding.editTextEmail.setText("")
                        binding.editTextPassword.setText("")
                        binding.editTextPasswordConfirm.setText("")
                    }
                } else {
                    binding.rootLayout.snackbar(response.message!!)
                }
                binding.progressBar.hide()

            } catch (e: ApiException) {
                binding.rootLayout.snackbar(e.message!!)
            } catch (e: NoInternetException) {
                binding.rootLayout.snackbar(e.message!!)
            }

        }

    }
}