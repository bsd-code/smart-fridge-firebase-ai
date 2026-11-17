package cat.institutmarianao.a1_pbackendalnvol.data.repository

import cat.institutmarianao.a1_pbackendalnvol.Ingredient
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI

class AIRecipeRepository {

    suspend fun generateRecipe(ingredients: List<Ingredient>): String {
        val ingredientsText = ingredients.joinToString(separator = "\n") { ingredient ->
            "- ${ingredient.name}: ${ingredient.quantity} ${ingredient.unit ?: ""} (${ingredient.category ?: ""})".trim()
        }

        val prompt = """
            Suggest a simple recipe I can make with the following ingredients: 
            $ingredientsText

            Please format the response using simple Markdown:
            - Use a main heading for the recipe **Title**.
            - Use subheadings like **Ingredients** and **Instructions** in bold.
            - Use bullet points for the ingredients list.
            - You can make key preparation steps **bold** as well.
        """.trimIndent()

        return try {
            val generativeModel = Firebase.vertexAI.generativeModel("gemini-1.5-flash")
            val response = generativeModel.generateContent(prompt)
            response.text ?: getFallbackRecipe(ingredientsText)
        } catch (e: Exception) {
            getFallbackRecipe(ingredientsText)
        }
    }

    private fun getFallbackRecipe(ingredientsText: String): String {
        return """
            # 🍳 Chef's Special Pantry Dish
            
            ## Ingredients
            $ingredientsText
            
            ## Instructions
            1. Prep all available ingredients by washing and cutting them into uniform pieces.
            2. **Heat a pan** over medium heat with a drizzle of oil or butter.
            3. **Saute the main ingredients** until golden brown and tender.
            4. **Season** with salt season to taste.
            5. **Serve hot** and enjoy your custom home-cooked meal!
        """.trimIndent()
    }
}
