package cat.institutmarianao.a1_pbackendalnvol

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etIngredientName : TextInputEditText
    private lateinit var etQuantity : TextInputEditText
    private lateinit var etExpirationDate : TextInputEditText
    private lateinit var spUnit : Spinner
    private lateinit var spCategory : Spinner
    private lateinit var btnAddUpdate : MaterialButton
    private lateinit var btnClear : MaterialButton
    private lateinit var rvIngredients : RecyclerView

    private lateinit var ingredientAdapter: IngredientAdapter
    private val ingredientList = mutableListOf<Ingredient>()

    private var selectedIngredient: Ingredient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        auth = FirebaseAuth.getInstance()

        initComponents()
        setupSpinners()
        // setupRecyclerView() -> Conectar el adaptador con el RecyclerView
        // initListeners()
        // loadIngredientsFromFirestore() -> Escuchar cambios en la colección del usuario
    }

    private fun initComponents(){
        etIngredientName = findViewById(R.id.etIngredientName)
        etQuantity = findViewById(R.id.etQuantity)
        etExpirationDate = findViewById(R.id.etExpirationDate)
        spUnit = findViewById(R.id.spUnit)
        spCategory = findViewById(R.id.spCategory)
        btnAddUpdate = findViewById(R.id.btnAddUpdateIngredient)
        btnClear = findViewById(R.id.btnClear)
        rvIngredients = findViewById(R.id.rvIngredients)
    }

    private fun setupSpinners(){
        val units = listOf("[Unit]", "units", "grams", "kg", "liters", "ml", "packs")
        val categories = listOf("[Category]", "Fruits & Vegetables", "Meat & Fish", "Dairy", "Beverages", "Frozen Foods", "Others")

        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spUnit.adapter = unitAdapter

        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = categoryAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_recipe -> {
                generateRecipeWithAI()
                true
            }
            R.id.action_logout -> {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

// private fun initListeners() {
//    - etExpirationDate: Al hacer clic, mostrar un DatePickerDialog para seleccionar la fecha
//    - btnAddUpdate: Si el ingrediente seleccionado es null -> Guardar nuevo en Firestore. Si no es null -> Actualizar existente en Firestore.
//    - btnClear: Llama a clearForm()
// }

// private fun clearForm() {
//    - Limpia el texto de todos los EditText
//    - Resetea los Spinners a la primera opción
//    - Cambia el texto del botón principal de nuevo a "Add Ingredient"
//    - Establece el ingrediente seleccionado a null
// }

// private fun showDeleteConfirmationDialog(ingredient: Ingredient) {
//    - Mostrar un AlertDialog preguntando si realmente desea eliminar el ingrediente
//    - Si confirma -> Borrar el documento de Firestore mediante su ID
// }

// private fun generateRecipeWithAI() {
//    1. Mostrar AlertDialog de carga ("Generating Recipe... Please wait...")
//    2. Recopilar los nombres y cantidades de los ingredientes actuales en un String
//    3. Crear la consulta (prompt) en inglés pidiendo respuesta en formato Markdown
//    4. Enviar la consulta a la API de Firebase Vertex AI / Gemini
//    5. Al recibir la respuesta: Ocultar el diálogo de carga e iniciar RecipeActivity pasando la receta mediante Intent
// }

    private fun generateRecipeWithAI(){

    }
}