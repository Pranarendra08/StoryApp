package com.example.storyapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.storyapp.R
import com.example.storyapp.api.request.RegisterRequest
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.helper.isEmailValid
import com.example.storyapp.ui.main.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            if (name.isNotEmpty() && email.isEmailValid() && password.length > 7) {
                val registerRequest = RegisterRequest(name, email, password)
                registerViewModel.register(registerRequest)
                Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.register_failed), Toast.LENGTH_SHORT).show()
            }

            registerViewModel.isSuccess.observe(this) {
                if (it == true) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
        playAnimation()
    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(500)
        val tvRegister = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(500)
        val tvName = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(500)
        val edName = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val edtEmail = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val edtPassword = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                logo,
                tvRegister,
                tvName,
                edName,
                tvEmail,
                edtEmail,
                tvPassword,
                edtPassword,
                btnRegister
            )
            start()
        }
    }
}