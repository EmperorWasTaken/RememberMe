package com.example.rememberme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rememberme.databinding.ActivityLoginBinding
import com.example.rememberme.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

private var TAG:String = "RememberMe:RegisterActivity"
private lateinit var binding: ActivityRegisterBinding
private lateinit var auth: FirebaseAuth
private lateinit var refUsers: DatabaseReference
private var fireBaseUserID:String = ""

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnLogin.setOnClickListener{
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        binding.registerNewUser.setOnClickListener{
            registerUser()
        }

        auth = FirebaseAuth.getInstance()
    }

    private fun registerUser() {
        val userEmail: String = userEmailRegister.text.toString().trim()
        val userPassword: String = userPasswordRegister.text.toString().trim()

        if(userEmail == ""){
            Toast.makeText(this, "You forgot to write your email!", Toast.LENGTH_SHORT).show()
        }else if(userPassword == ""){
            Toast.makeText(this, "You forgot to write your password!", Toast.LENGTH_SHORT).show()
        }else {
            auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    fireBaseUserID = auth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(fireBaseUserID)

                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = fireBaseUserID
                    userHashMap["useremail"] = userEmail
                    userHashMap["userpassword"] = userPassword

                    refUsers.updateChildren(userHashMap).addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            Toast.makeText(this, "User successfully created!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, LoginActivity::class.java))
                            finish()
                        }
                    }


                } else {

                }

            }
        }

    }
}