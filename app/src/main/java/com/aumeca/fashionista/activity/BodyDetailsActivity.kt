package com.aumeca.fashionista.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import com.aumeca.fashionista.R
import com.aumeca.fashionista.model.HealthDetails

class BodyDetailsActivity : BaseActivity() {
    private lateinit var ageInput: EditText
    private lateinit var gender: Spinner
    private lateinit var weightInput: EditText
    private lateinit var heightInput: EditText
    private lateinit var workoutType: Spinner
    private lateinit var durationInput: EditText
    private lateinit var diseaseInput: EditText
    private lateinit var submitDetails: Button
    var health: HealthDetails ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_body_details)
        setupActionBar()
        ageInput = findViewById(R.id.ageInput)
        gender = findViewById(R.id.genderSpinner)
        weightInput = findViewById(R.id.weightInput)
        heightInput = findViewById(R.id.heightInput)
        workoutType = findViewById(R.id.workoutTypeSpinner)
        durationInput = findViewById(R.id.durationInput)
        diseaseInput = findViewById(R.id.disease_input)
        submitDetails = findViewById(R.id.submitDetails)

        submitDetails.setOnClickListener{
            if(validateForm(ageInput.text.toString(),gender.selectedItem.toString(),weightInput.text.toString(),heightInput.text.toString(),workoutType.selectedItem.toString(),durationInput.text.toString())){
                showProgressDialog(resources.getString(R.string.please_wait))
                // Correct initialization without the extra closing parenthesis
                 health = HealthDetails(
                    ageInput.text.toString(),
                    gender.selectedItem.toString(),
                    weightInput.text.toString(),
                    heightInput.text.toString(),
                    workoutType.selectedItem.toString(),
                    durationInput.text.toString(),
                    diseaseInput.text.toString()
                )
                val resultIntent = Intent()
                resultIntent.putExtra("key", health)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

        }
    }

    private fun setupActionBar() {
        val toolbar_my_taskList_activity: Toolbar = findViewById(R.id.toolbar_details_activity)
        setSupportActionBar(toolbar_my_taskList_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = "Health Details"
        }
        toolbar_my_taskList_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateForm(age:String,gen:String,weight:String,height:String,workout:String,duration:String):Boolean{
        return when{

            TextUtils.isEmpty(age)->{
                showErrorSnackBar("Please enter an age")
                false
            }

            TextUtils.isEmpty(gen)->{
                showErrorSnackBar("Please select gender")
                false
            }
            TextUtils.isEmpty(weight)->{
                showErrorSnackBar("Please enter a weight")
                false
            }
            TextUtils.isEmpty(height)->{
                showErrorSnackBar("Please enter a height")
                false
            }
            TextUtils.isEmpty(duration)->{
                showErrorSnackBar("Please enter a duration")
                false
            }
            TextUtils.isEmpty(workout)->{
                showErrorSnackBar("Please select a workout type")
                false
            }

            else->{
                true
            }
        }
    }
}