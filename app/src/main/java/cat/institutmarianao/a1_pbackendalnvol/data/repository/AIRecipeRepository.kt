package cat.institutmarianao.a1_pbackendalnvol.data.repository

import android.util.Log
import cat.institutmarianao.a1_pbackendalnvol.Ingredient
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI

class AIRecipeRepository {

    companion object {
        private const val TAG = "AIRecipeRepository"
    }

    suspend fun generateRecipe(ingredients: List<Ingredient>): String {
        val ingredientsText = ingredients.joinToString(separator = "\n") { ingredient ->
            "- ${ingredient.name}: ${ingredient.quantity} ${ingredient.unit ?: ""} (${ingredient.category ?: ""})".trim()
        }

        val prompt = "Suggest a simple recipe I can make with the following ingredients:\n$ingredientsText\n\nPlease format the response using simple Markdown:\n- Use a main heading for the recipe Title.\n- Use subheadings like Ingredients and Instructions in bold.\n- Use bullet points for the ingredients list.\n- You can make key preparation steps bold as well."

        Log.d(TAG, "Requesting AI recipe generation using Firebase AI / Gemini for ${ingredients.size} ingredients")
        return try {
            val generativeModel = Firebase.vertexAI.generativeModel("gemini-1.5-flash")
            val response = generativeModel.generateContent(prompt)
            Log.d(TAG, "AI response received successfully")
            response.text ?: getFallbackRecipe(ingredientsText)
        } catch (e: Exception) {
            Log.e(TAG, "Firebase AI request failed or service not available, using fallback: ${e.message}")
            getFallbackRecipe(ingredientsText)
        }
    }

    private fun getFallbackRecipe(ingredientsText: String): String {
        return "# 🍳 Chef's Special Pantry Dish\n\n## Ingredients\n$ingredientsText\n\n## Instructions\n1. Prep all available ingredients by washing and cutting them into uniform pieces.\n2. **Heat a pan** over medium heat with a drizzle of oil or butter.\n3. **Saute the main ingredients** until golden brown and tender.\n4. **Season** with salt, pepper, and herbs to taste.\n5. **Serve hot** and enjoy your custom home-cooked meal!"
    }
}
