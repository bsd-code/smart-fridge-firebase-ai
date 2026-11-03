package cat.institutmarianao.a1_pbackendalnvol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IngredientAdapter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ingredient_adapter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

// ESTA CLASE ES UN ADAPTADOR DE RECYCLERVIEW (No es una Activity)
// Debe heredar de: RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>()
// Parámetros a recibir en el constructor:
// 1. Lista mutable de objetos Ingredient
// 2. Función de callback al hacer clic en un elemento (para cargar datos en el formulario)
// 3. Función de callback al pulsar la papelera (para pedir confirmación y eliminar)
// Tareas a implementar en esta clase:
// - Crear la clase interna ViewHolder que vincule los elementos de item_ingredient.xml (tvName, tvQuantity, tvExpiration, btnDelete)
// - onCreateViewHolder: Inflar la vista de item_ingredient.xml
// - onBindViewHolder: Pintar los datos del ingrediente en las vistas y asignar los setOnClickListener (tarjeta completa y botón eliminar)
// - getItemCount: Devolver el tamaño de la lista
}