package com.example.rememberme.userActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rememberme.MainActivity
import com.example.rememberme.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

private var TAG:String = "RememberMe:LoginActivity"
private lateinit var binding: ActivityLoginBinding
private lateinit var auth: FirebaseAuth
private lateinit var refUsers: DatabaseReference
private var fireBaseUserID:String = ""


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.loginButton.setOnClickListener{
            loginUser()
        }


        binding.registerUser.setOnClickListener{
            registerNewUser()
        }

        binding.forgotPassword.setOnClickListener{
            forgotUserPassword()
        }
    }


    private fun loginUser() {
        val userEmail = usernameInput.text.toString().trim()
        val userPassword = passwordInput.text.toString().trim()

        when {
            userEmail.isEmpty() -> Toast.makeText(this, "Oops, forgot you email", Toast.LENGTH_SHORT).show()
            userPassword.isEmpty() -> Toast.makeText(this, "Oops, forgot your password? You can press 'Forgot Password' if you did", Toast.LENGTH_SHORT).show()
            else -> {
                auth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            Log.d(TAG, "Logged in")
                        } else {
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                            Log.w(TAG, "Not logged in", task.exception)
                        }
                    }
            }

        }
    }

    private fun registerNewUser(){
        startActivity(Intent(applicationContext, RegisterActivity::class.java))
    }
    private fun forgotUserPassword() {
        startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
    }

}