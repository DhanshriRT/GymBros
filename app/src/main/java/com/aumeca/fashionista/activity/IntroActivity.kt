package com.aumeca.fashionista.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.aumeca.fashionista.R

class IntroActivity : AppCompatActivity() {
    private lateinit var btn_sign_up: Button
    private lateinit var btn_sign_in: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        btn_sign_up = findViewById(R.id.btn_sign_up_intro)

        btn_sign_up.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        btn_sign_in = findViewById(R.id.btn_sign_in_intro)

        btn_sign_in.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}