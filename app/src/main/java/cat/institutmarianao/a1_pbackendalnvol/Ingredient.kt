package cat.institutmarianao.a1_pbackendalnvol

// Modelo de datos para Firestore
data class Ingredient(
    var id: String = "",
    var name: String = "",
    var quantity: String = "",
    var unit: String = "",
    var expirationDate: String = "",
    var category: String = ""
)