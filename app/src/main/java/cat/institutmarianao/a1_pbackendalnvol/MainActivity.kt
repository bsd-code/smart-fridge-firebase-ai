package cat.institutmarianao.a1_pbackendalnvol

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.institutmarianao.a1_pbackendalnvol.data.repository.AIRecipeRepository
import cat.institutmarianao.a1_pbackendalnvol.data.repository.AuthRepository
import cat.institutmarianao.a1_pbackendalnvol.data.repository.IngredientRepository
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val authRepository = AuthRepository()
    private val ingredientRepository = IngredientRepository()
    private val aiRecipeRepository = AIRecipeRepository()

    private var snapshotListener: ListenerRegistration? = null

    private lateinit var etIngredientName: TextInputEditText
    private lateinit var etQuantity: TextInputEditText
    private lateinit var etExpirationDate: TextInputEditText
    private lateinit var spUnit: Spinner
    private lateinit var spCategory: Spinner
    private lateinit var btnAddUpdate: MaterialButton
    private lateinit var btnClear: MaterialButton
    private lateinit var rvIngredients: RecyclerView

    private lateinit var ingredientAdapter: IngredientAdapter
    private val ingredientList = mutableListOf<Ingredient>()

    private var selectedIngredient: Ingredient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!authRepository.isUserLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        initComponents()
        setupSpinners()
        setupRecyclerView()
        initListeners()
        loadIngredientsFromFirestore()
    }

    private fun initComponents() {
        etIngredientName = findViewById(R.id.etIngredientName)
        etQuantity = findViewById(R.id.etQuantity)
        etExpirationDate = findViewById(R.id.etExpirationDate)
        spUnit = findViewById(R.id.spUnit)
        spCategory = findViewById(R.id.spCategory)
        btnAddUpdate = findViewById(R.id.btnAddUpdateIngredient)
        btnClear = findViewById(R.id.btnClear)
        rvIngredients = findViewById(R.id.rvIngredients)
    }

    private fun setupSpinners() {
        val units = listOf("[Unit]", "units", "grams", "kg", "liters", "ml", "packs")
        val categories = listOf(
            "[Category]",
            "Fruit & Vegs",
            "Meat & Fish",
            "Dairy & Eggs",
            "Bakery & Bread",
            "Grains & Pasta",
            "Sauces & Condiments",
            "Drinks",
            "Leftovers",
            "Other"
        )

        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spUnit.adapter = unitAdapter

        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = categoryAdapter
    }

    private fun setupRecyclerView() {
        ingredientAdapter = IngredientAdapter(
            ingredientList,
            onItemClick = { ingredient -> populateFormForEdit(ingredient) },
            onDeleteClick = { ingredient -> showDeleteConfirmationDialog(ingredient) }
        )
        rvIngredients.layoutManager = LinearLayoutManager(this)
        rvIngredients.adapter = ingredientAdapter
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
                authRepository.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearForm() {
        etIngredientName.setText("")
        etQuantity.setText("")
        etExpirationDate.setText("")
        spUnit.setSelection(0)
        spCategory.setSelection(0)
        btnAddUpdate.text = getString(R.string.add_ingredient)
        selectedIngredient = null
    }

    private fun initListeners() {
        etExpirationDate.setOnClickListener {
            showDatePickerDialog()
        }

        btnAddUpdate.setOnClickListener {
            saveOrUpdateIngredient()
        }

        btnClear.setOnClickListener {
            clearForm()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(
                    Locale.getDefault(),
                    "%02d/%02d/%04d",
                    selectedDay,
                    selectedMonth + 1,
                    selectedYear
                )
                etExpirationDate.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun populateFormForEdit(ingredient: Ingredient) {
        selectedIngredient = ingredient
        etIngredientName.setText(ingredient.name)
        etQuantity.setText(ingredient.quantity.toString())
        etExpirationDate.setText(ingredient.expirationDate ?: "")

        val units = (spUnit.adapter as ArrayAdapter<String>)
        for (i in 0 until units.count) {
            if (units.getItem(i) == ingredient.unit) {
                spUnit.setSelection(i)
                break
            }
        }

        val categories = (spCategory.adapter as ArrayAdapter<String>)
        for (i in 0 until categories.count) {
            if (categories.getItem(i) == ingredient.category) {
                spCategory.setSelection(i)
                break
            }
        }

        btnAddUpdate.text = "Update Ingredient"
    }

    private fun loadIngredientsFromFirestore() {
        val userId = authRepository.currentUserUid ?: return
        snapshotListener?.remove()
        snapshotListener = ingredientRepository.listenIngredients(
            userId = userId,
            onUpdate = { list ->
                ingredientList.clear()
                ingredientList.addAll(list)
                ingredientAdapter.notifyDataSetChanged()
            },
            onError = { errorMsg ->
                Toast.makeText(this, "Error loading ingredients: $errorMsg", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun saveOrUpdateIngredient() {
        val name = etIngredientName.text?.toString()?.trim().orEmpty()
        val quantityStr = etQuantity.text?.toString()?.trim().orEmpty()
        val expirationDate = etExpirationDate.text?.toString()?.trim().orEmpty()
        val unit = if (spUnit.selectedItemPosition > 0) spUnit.selectedItem.toString() else null
        val category = if (spCategory.selectedItemPosition > 0) spCategory.selectedItem.toString() else null

        if (name.isEmpty()) {
            etIngredientName.error = "Name cannot be empty"
            return
        }
        if (quantityStr.isEmpty()) {
            etQuantity.error = "Quantity cannot be empty"
            return
        }

        val quantity = quantityStr.toIntOrNull() ?: 1
        val userId = authRepository.currentUserUid ?: return

        if (selectedIngredient == null) {
            val ingredient = Ingredient(
                name = name,
                quantity = quantity,
                unit = unit,
                expirationDate = expirationDate,
                category = category
            )
            ingredientRepository.addIngredient(userId, ingredient) { success, errorMsg ->
                if (success) {
                    Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show()
                    clearForm()
                } else {
                    Toast.makeText(this, "Failed to add: $errorMsg", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            val currentId = selectedIngredient!!.id
            val updatedMap = mutableMapOf<String, Any?>(
                "name" to name,
                "quantity" to quantity,
                "unit" to unit,
                "expirationDate" to expirationDate,
                "category" to category
            )
            ingredientRepository.updateIngredient(userId, currentId, updatedMap) { success, errorMsg ->
                if (success) {
                    Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show()
                    clearForm()
                } else {
                    Toast.makeText(this, "Failed to update: $errorMsg", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showDeleteConfirmationDialog(ingredient: Ingredient) {
        AlertDialog.Builder(this)
            .setTitle("Delete Ingredient")
            .setMessage("Are you sure you want to delete ${ingredient.name}?")
            .setPositiveButton("Delete") { _, _ ->
                val userId = authRepository.currentUserUid ?: return@setPositiveButton
                ingredientRepository.deleteIngredient(userId, ingredient.id) { success, errorMsg ->
                    if (success) {
                        Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show()
                        if (selectedIngredient?.id == ingredient.id) {
                            clearForm()
                        }
                    } else {
                        Toast.makeText(this, "Failed to delete: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun generateRecipeWithAI() {
        if (ingredientList.isEmpty()) {
            Toast.makeText(this, "Please add some ingredients first!", Toast.LENGTH_SHORT).show()
            return
        }

        val progressDialog = AlertDialog.Builder(this)
            .setTitle("AI Recipe Suggestion")
            .setMessage("Generating Recipe... Please wait...")
            .setCancelable(false)
            .create()
        progressDialog.show()

        lifecycleScope.launch {
            val recipeMarkdown = aiRecipeRepository.generateRecipe(ingredientList)
            progressDialog.dismiss()
            val intent = Intent(this@MainActivity, RecipeActivity::class.java)
            intent.putExtra("RECIPE_TEXT", recipeMarkdown)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        snapshotListener?.remove()
        snapshotListener = null
    }
}
