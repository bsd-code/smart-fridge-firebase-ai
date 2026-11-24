package cat.institutmarianao.a1_pbackendalnvol.data.repository

import android.util.Log
import cat.institutmarianao.a1_pbackendalnvol.Ingredient
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class IngredientRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    companion object {
        private const val TAG = "IngredientRepository"
    }

    fun listenIngredients(
        userId: String,
        onUpdate: (List<Ingredient>) -> Unit,
        onError: (String) -> Unit
    ): ListenerRegistration {
        Log.d(TAG, "Subscribing to snapshot listener for userId: $userId")
        return db.collection("users")
            .document(userId)
            .collection("ingredients")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error in snapshot listener: ${error.message}")
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
                            val expirationDate = doc.getString("expiration") ?: doc.getString("expirationDate")

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
                            Log.e(TAG, "Error parsing document ${doc.id}: ${e.message}")
                        }
                    }
                    Log.d(TAG, "Loaded ${list.size} ingredients for user $userId")
                    onUpdate(list)
                }
            }
    }

    fun addIngredient(userId: String, ingredient: Ingredient, onResult: (Boolean, String?) -> Unit) {
        val ref = db.collection("users").document(userId).collection("ingredients").document()
        ingredient.id = ref.id
        Log.d(TAG, "Adding new ingredient with id: ${ingredient.id}")

        val docData = hashMapOf(
            "id" to ingredient.id,
            "name" to ingredient.name,
            "quantity" to ingredient.quantity,
            "unit" to ingredient.unit,
            "category" to ingredient.category,
            "expiration" to ingredient.expirationDate,
            "expirationDate" to ingredient.expirationDate,
            "addedAt" to FieldValue.serverTimestamp()
        )

        ref.set(docData)
            .addOnSuccessListener {
                Log.d(TAG, "Ingredient added successfully")
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding ingredient: ${e.message}")
                onResult(false, e.localizedMessage)
            }
    }

    fun updateIngredient(
        userId: String,
        ingredientId: String,
        updatedMap: Map<String, Any?>,
        onResult: (Boolean, String?) -> Unit
    ) {
        Log.d(TAG, "Updating ingredient id: $ingredientId")
        db.collection("users")
            .document(userId)
            .collection("ingredients")
            .document(ingredientId)
            .update(updatedMap)
            .addOnSuccessListener {
                Log.d(TAG, "Ingredient updated successfully")
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating ingredient: ${e.message}")
                onResult(false, e.localizedMessage)
            }
    }

    fun deleteIngredient(userId: String, ingredientId: String, onResult: (Boolean, String?) -> Unit) {
        Log.d(TAG, "Deleting ingredient id: $ingredientId")
        db.collection("users")
            .document(userId)
            .collection("ingredients")
            .document(ingredientId)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Ingredient deleted successfully")
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error deleting ingredient: ${e.message}")
                onResult(false, e.localizedMessage)
            }
    }
}
