package com.example.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.storyapp.R
import com.example.storyapp.ui.main.MainActivity
import com.example.storyapp.ui.register.RegisterActivity
import com.example.storyapp.databinding.ActivityLoginBinding
import androidx.activity.viewModels
import com.example.storyapp.api.request.LoginRequest
import com.example.storyapp.helper.AuthPreference
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.helper.isEmailValid

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var authPreference: AuthPreference

    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        authPreference = AuthPreference(this)

        if (authPreference.getValue("status").equals("1")) {
            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }

        loginViewModel.isSuccess.observe(this) {
            authCheck(it)
        }

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)

        playAnimation()
    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(500)
        val tvlogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val edtEmail = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(500)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val edtPassword = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val line1 = ObjectAnimator.ofFloat(binding.ivLine1, View.ALPHA, 1f).setDuration(500)
        val tvOr = ObjectAnimator.ofFloat(binding.tvOr, View.ALPHA, 1f).setDuration(500)
        val line2 = ObjectAnimator.ofFloat(binding.ivLine2, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(line1, tvOr, line2)
        }

        AnimatorSet().apply {
            playSequentially(
                logo,
                tvlogin,
                tvEmail,
                edtEmail,
                tvPassword,
                edtPassword,
                btnLogin,
                together,
                btnRegister
            )
            start()
        }
    }

    private fun authCheck(isSuccess: Boolean) {
        if (isSuccess) {
            finish()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val email = binding.edLoginEmail.text.toString()
                val password = binding.edLoginPassword.text.toString()

                if (email.isNotEmpty() && email.isEmailValid() && password.isNotEmpty() && password.length > 7) {
                    val loginRequest = LoginRequest(email, password)
                    loginViewModel.login(loginRequest)
                    Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

}