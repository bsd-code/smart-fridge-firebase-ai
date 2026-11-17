package cat.institutmarianao.a1_pbackendalnvol

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

// Modelo de datos para Firestore
data class Ingredient(
    @get:Exclude var id: String = "",
    var name: String = "",
    var quantity: Int = 1,
    var unit: String? = null,
    var category: String? = null,
    var expirationDate: String? = null,
    @ServerTimestamp var addedAt: Date? = null
)
