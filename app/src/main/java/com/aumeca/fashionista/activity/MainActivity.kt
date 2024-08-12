package com.aumeca.fashionista.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.aumeca.fashionista.R
import com.aumeca.fashionista.geminiAI.GeminiClass
import com.aumeca.fashionista.model.Goal
import com.aumeca.fashionista.model.HealthDetails
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {
    private lateinit var enterDetails: TextView
    private lateinit var enterGoal: TextView
    private lateinit var getPlan: Button
    var healthObj: HealthDetails? = null
    var goalObj: Goal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        enterDetails = findViewById(R.id.enterDetails)
        enterGoal = findViewById(R.id.enterGoal)
        getPlan = findViewById(R.id.hitTheGym)

        enterDetails.setOnClickListener {

            val intent = Intent(this, BodyDetailsActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_FOR_BODY_DETAILS)
        }
        enterGoal.setOnClickListener {
            Toast.makeText(this, "Goal", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, GoalActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_FOR_GOAL)
        }
        getPlan.setOnClickListener {
                if(healthObj==null){
                showErrorSnackBar("Enter the body details first")
            }
            else if(goalObj==null){
                showErrorSnackBar("Enter the goal first")
        }
            else{

                CoroutineScope(Dispatchers.IO).launch {
//
                    var result = GeminiClass().getResponseFromGemini(healthObj!!, goalObj!!)
//
                    result = formatGeminiResult(result).toString()
                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@MainActivity, PreviousPlanActivity::class.java)
                        intent.putExtra("data", result)
                        startActivity(intent)
                    }
                }
                hideProgressDialog()


                val intent = Intent(this, PreviousPlanActivity::class.java)
                intent.putExtra("IsDone", false)
                startActivity(intent)
            }
        }
    }

    private fun setupActionBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main_activity)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)

            // Inflate the custom view for the title
            val customTitleView = layoutInflater.inflate(R.layout.custom_title_bar, null)
            actionBar.customView = customTitleView

            // Optional: Adjust the custom view layout parameters
            val params = Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT
            )
            customTitleView.layoutParams = params
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, IntroActivity::class.java)

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_FOR_BODY_DETAILS && resultCode == Activity.RESULT_OK) {
            healthObj = data?.getParcelableExtra<HealthDetails>("key")
            if (healthObj != null) {
                // Handle the returned object
                findViewById<CircleImageView>(R.id.step1).setImageResource(R.drawable.done_img)

            }else{
                findViewById<CircleImageView>(R.id.step1).setImageResource(R.drawable.steps_bullet)
            }
        }

        if (requestCode == REQUEST_CODE_FOR_GOAL && resultCode == Activity.RESULT_OK) {
            goalObj = data?.getParcelableExtra<Goal>("key")
            if (goalObj != null) {
                // Handle the returned object
                findViewById<CircleImageView>(R.id.step2).setImageResource(R.drawable.done_img)
            }else{
                findViewById<CircleImageView>(R.id.step2).setImageResource(R.drawable.steps_bullet)
            }
        }
    }
    fun onUserDataSavedSuccessfully() {
        // Handle what happens after the user data is successfully saved
        // For example, navigate to another screen or update the UI
    }

    fun formatGeminiResult(result: String): SpannableStringBuilder {
        val formattedResult = SpannableStringBuilder()

        // Split the result into lines
        val lines = result.split("\n")
        var exerciseNumber = 1

        for (line in lines) {
            if (line.isNotBlank()) {
                when {
                    line.contains("**") -> {
                        // If the line is a header or important section
                        formattedResult.append("\n${line.trim()}\n\n", StyleSpan(Typeface.BOLD), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    line.contains("* ") -> {
                        // If it's an exercise line, number it and bold the exercise name
                        val colonIndex = line.indexOf(':')
                        if (colonIndex != -1) {
                            val exerciseName = line.substring(2, colonIndex)
                            val details = line.substring(colonIndex + 1).trim()

                            formattedResult.append("${exerciseNumber++}. ")
                            formattedResult.append(exerciseName, StyleSpan(Typeface.BOLD), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            formattedResult.append(": $details\n\n")
                        } else {
                            formattedResult.append("${exerciseNumber++}. ${line.substring(2)}\n\n")
                        }
                    }
                    else -> {
                        // Regular text, just add it
                        formattedResult.append(line.trim() + "\n\n")
                    }
                }
            }
        }

        // Add lines between sections
        val regexSection = Regex("Warm-up|Strength Building|Cool Down", RegexOption.IGNORE_CASE)
        formattedResult.insert(0, "\n")
        var matcher = regexSection.findAll(formattedResult.toString())
        matcher.forEach {
            formattedResult.insert(it.range.first, "\n-----------------------------\n")
        }

        return formattedResult
    }




    companion object {
        const val REQUEST_CODE_FOR_BODY_DETAILS = 100
        const val REQUEST_CODE_FOR_GOAL = 200
    }
}