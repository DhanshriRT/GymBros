package com.aumeca.fashionista.geminiAI

import com.aumeca.fashionista.BuildConfig
import com.aumeca.fashionista.model.Goal
import com.aumeca.fashionista.model.HealthDetails
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiClass {
    private lateinit var generativeModel: GenerativeModel

    fun initGemini() {
        generativeModel = GenerativeModel(
            "gemini-1.5-pro-latest",
            apiKey = BuildConfig.apiKey,
            generationConfig = generationConfig {
                temperature = 0.4f
                topK = 32
                topP = 1f
                maxOutputTokens = 4096
            },
            safetySettings = listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE)
            )
        )
    }

    suspend fun getResponseFromGemini(health: HealthDetails, goal: Goal): String {
        initGemini()

        val question = "Tell whole sequence of workouts in points for a person having health parameters as gender = ${health.gender}, age = ${health.age}, weight = ${health.weight}, height = ${health.height}, for workout type = ${health.workoutType}, for duration of ${health.duration} minutes and the goal is ${goal.goal} the person has ${health.disease} along with diet plan"

        return withContext(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(question)
                    }
                )
                response.text.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                "Not able to generate the plan! Try Later"
            }
        }
    }
}
