package com.aumeca.fashionista.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import com.aumeca.fashionista.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        val btn_signIn: Button = findViewById(R.id.btn_sign_in)
        btn_signIn.setOnClickListener {
            signIn()
        }
    }

    private fun setupActionBar() {

        val toolbar_sign_in_activity = findViewById<Toolbar>(R.id.toolbar_sign_in_activity)
        setSupportActionBar(toolbar_sign_in_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_sign_in_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun signIn(){
        val et_email = findViewById<TextView>(R.id.et_email)
        val et_password = findViewById<TextView>(R.id.et_password)

        val email: String = et_email.text.toString().trim{it <= ' '}
        val password: String = et_password.text.toString().trim{it <= ' '}

        if(validateForm(email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(){ task->
                hideProgressDialog()
                if(task.isSuccessful){
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val email1 = firebaseUser.email

                    Toast.makeText(this, "$email1 successfully signed in!!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                }
                else{
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun validateForm(email: String, password:String):Boolean{
        return when{

            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter an Email")
                false
            }

            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter a password")
                false
            }
            else->{
                true
            }
        }
    }
}