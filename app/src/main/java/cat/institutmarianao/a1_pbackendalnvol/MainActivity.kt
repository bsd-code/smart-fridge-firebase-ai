package cat.institutmarianao.a1_pbackendalnvol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

// 1. Declarar variables lateinit para vistas (etIngredientName, etQuantity, etExpirationDate, spUnit, spCategory, btnAddUpdate, btnClear, rvIngredients)
// 2. Declarar variable para guardar el ingrediente seleccionado actualmente (null si estamos creando uno nuevo)
// 3. Declarar el adaptador del RecyclerView y la lista local de ingredientes

// override fun onCreate(...) {
//    setContentView(R.layout.activity_main)
//    - initComponents()
//    - setupSpinners() -> Cargar opciones en los desplegables de Unit y Category
//    - setupRecyclerView() -> Conectar el adaptador con el RecyclerView
//    - initListeners()
//    - loadIngredientsFromFirestore() -> Escuchar cambios en la colección del usuario
// }

// override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//    Inflar el menú de [app/src/main/res/menu/menu_main.xml](file:///home/cfgs/AndroidStudioProjects/A1PBackendalnvol/app/src/main/res/menu/menu_main.xml)
// }

// override fun onOptionsItemSelected(item: MenuItem): Boolean {
//    - Si pulsa icono Receta -> llamar a generateRecipeWithAI()
//    - Si pulsa icono Salir -> cerrar sesión en FirebaseAuth y volver a LoginActivity
// }

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
}