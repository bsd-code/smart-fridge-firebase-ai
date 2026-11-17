package cat.institutmarianao.a1_pbackendalnvol.data.repository

import cat.institutmarianao.a1_pbackendalnvol.Ingredient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class IngredientRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    fun listenIngredients(
        userId: String,
        onUpdate: (List<Ingredient>) -> Unit,
        onError: (String) -> Unit
    ): ListenerRegistration {
        return db.collection("users")
            .document(userId)
            .collection("ingredients")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onError(error.message ?: "Unknown Firestore error")
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = mutableListOf<Ingredient>()
                    for (doc in snapshot.documents) {
                        try {
                            val name = doc.getString("name").orEmpty()
                            val rawQuantity = doc.get("quantity")
                            val quantityInt = when (rawQuantity) {
                                is Number -> rawQuantity.toInt()
                                is String -> rawQuantity.toIntOrNull() ?: 1
                                else -> 1
                            }
                            val unit = doc.getString("unit")
                            val category = doc.getString("category")
                            val expirationDate = doc.getString("expirationDate")

                            val ingredient = Ingredient(
                                id = doc.id,
                                name = name,
                                quantity = quantityInt,
                                unit = unit,
                                category = category,
                                expirationDate = expirationDate
                            )
                            list.add(ingredient)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    onUpdate(list)
                }
            }
    }

    fun addIngredient(userId: String, ingredient: Ingredient, onResult: (Boolean, String?) -> Unit) {
        val ref = db.collection("users").document(userId).collection("ingredients").document()
        ingredient.id = ref.id
        ref.set(ingredient)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { e -> onResult(false, e.localizedMessage) }
    }

    fun updateIngredient(
        userId: String,
        ingredientId: String,
        updatedMap: Map<String, Any?>,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .collection("ingredients")
            .document(ingredientId)
            .update(updatedMap)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { e -> onResult(false, e.localizedMessage) }
    }

    fun deleteIngredient(userId: String, ingredientId: String, onResult: (Boolean, String?) -> Unit) {
        db.collection("users")
            .document(userId)
            .collection("ingredients")
            .document(ingredientId)
            .delete()
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { e -> onResult(false, e.localizedMessage) }
    }
}
