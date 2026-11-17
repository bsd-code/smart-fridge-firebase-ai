package cat.institutmarianao.a1_pbackendalnvol

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import io.noties.markwon.Markwon

class RecipeActivity : AppCompatActivity() {

    private lateinit var tvRecipeContent: TextView
    private lateinit var toolbarRecipe: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        toolbarRecipe = findViewById(R.id.toolbarRecipe)
        tvRecipeContent = findViewById(R.id.tvRecipeContent)

        toolbarRecipe.setNavigationOnClickListener {
            finish()
        }

        val recipeText = intent.getStringExtra("RECIPE_TEXT") ?: "No recipe content found."
        val markwon = Markwon.create(this)
        markwon.setMarkdown(tvRecipeContent, recipeText)
    }
}
