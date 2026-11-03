package cat.institutmarianao.a1_pbackendalnvol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Ingredient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.item_ingredient)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

// ESTA CLASE ES UN MODELO DE DATOS (No es una Activity)
// Definir como: data class Ingredient(...)
// Debe contener las variables necesarias para representar un ingrediente en Firestore:
// - id (String) -> Identificador del documento en Firestore
// - name (String) -> Nombre del ingrediente
// - quantity (String o Double) -> Cantidad ingresada
// - unit (String) -> Unidad seleccionada en el Spinner
// - expirationDate (String) -> Fecha elegida
// - category (String) -> Categoría elegida en el Spinner

}