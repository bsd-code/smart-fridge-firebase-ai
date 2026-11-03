package cat.institutmarianao.a1_pbackendalnvol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

// 1. Declarar variable lateinit para el TextView que mostrará la receta (recipeTextView)

// override fun onCreate(...) {
//    setContentView(R.layout.activity_recipe)
//    - Vincular recipeTextView mediante findViewById
//    - Obtener el String de la receta enviado desde MainActivity a través del Intent (intent.getStringExtra("..."))
//    - Crear una instancia de Markwon (Markwon.create(this))
//    - Renderizar el Markdown recibido en el TextView: markwon.setMarkdown(recipeTextView, recipeText)
// }
}