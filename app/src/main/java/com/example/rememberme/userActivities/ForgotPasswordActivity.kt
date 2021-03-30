package com.example.rememberme.userActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.rememberme.databinding.ActivityForgotPasswordBinding
import com.example.rememberme.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forgot_password.*

private var TAG:String = "RememberMe:ForgotPasswordActivity"


private lateinit var binding: ActivityForgotPasswordBinding
private lateinit var auth: FirebaseAuth


class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.forgotPasswordButton.setOnClickListener{

            resetPassword()

        }

    }

    private fun resetPassword(){

        val userEmail = forgotPasswordEmail.text.toString().trim()

        if(userEmail == ""){
            Toast.makeText(this, "Oops, forgot you email", Toast.LENGTH_SHORT).show()
        }
        auth.sendPasswordResetEmail(userEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    Toast.makeText(this, "Password is reset! taking you back to login now", Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                } else {
                    Log.e("Error:" + task.exception, "Error")
                }
            }
    }
}