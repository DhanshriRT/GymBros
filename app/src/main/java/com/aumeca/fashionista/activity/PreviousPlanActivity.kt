package com.aumeca.fashionista.activity

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import com.aumeca.fashionista.R

class PreviousPlanActivity : BaseActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_previous_plan)

        setupActionBar()

        // Initialize the ProgressBar
        progressBar = findViewById(R.id.progressBar)

        // Show progress bar
        showProgressBar()

        Toast.makeText(this, "Helooo", Toast.LENGTH_SHORT).show()
        val value = intent.getStringExtra("data")

        // Hide progress bar after some operation
        hideProgressBar()

        if (value != null) {
            findViewById<TextView>(R.id.setResult).text = value
        }

        hideProgressBar()
    }


    private fun setupActionBar() {
        val toolbar_my_taskList_activity: Toolbar = findViewById(R.id.toolbar_prev_plan_activity)
        setSupportActionBar(toolbar_my_taskList_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Your WorkOut Plan!"
        }
        toolbar_my_taskList_activity.setNavigationOnClickListener { finish()  }
    }

    private fun showProgressBar() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = ProgressBar.GONE
    }
}
