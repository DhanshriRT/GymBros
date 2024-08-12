package com.aumeca.fashionista.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.aumeca.fashionista.R
import com.aumeca.fashionista.firebase.FirestoreClass

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        supportActionBar?.hide()
//        val typeFace: Typeface = Typeface.createFromAsset(assets, "carbon bl.ttf")
//        val txt: TextView = findViewById(R.id.SplashTextView)
//        txt.typeface = typeFace

        Handler().postDelayed({

            val currentUserId = FirestoreClass(this).getCurrentUserId()

            if(currentUserId.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
            }
            else
                startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }, 2500)

    }
}