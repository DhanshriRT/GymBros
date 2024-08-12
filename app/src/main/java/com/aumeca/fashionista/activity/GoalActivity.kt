package com.aumeca.fashionista.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import com.aumeca.fashionista.R
import com.aumeca.fashionista.model.Goal

class GoalActivity : BaseActivity() {
    private lateinit var goal: Spinner
    private lateinit var submitButton: Button
    var gObj: Goal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goal)

        setupActionBar()
//
        goal = findViewById(R.id.goal_workout_spinner)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            if(validateForm(goal.selectedItem.toString())){
                showProgressDialog(resources.getString(R.string.please_wait))
                // Correct initialization without the extra closing parenthesis
                    gObj = Goal(
                    goal.selectedItem.toString())

                val resultIntent = Intent()
                resultIntent.putExtra("key", gObj)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun setupActionBar() {
        val toolbar_my_taskList_activity: Toolbar = findViewById(R.id.toolbar_goal_activity)
        setSupportActionBar(toolbar_my_taskList_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Health Details"
        }
        toolbar_my_taskList_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateForm(g: String):Boolean{
        return when{

            TextUtils.isEmpty(g)->{
                showErrorSnackBar("Please enter goal")
                false
            }

            else->{
                true
            }
        }
    }
}