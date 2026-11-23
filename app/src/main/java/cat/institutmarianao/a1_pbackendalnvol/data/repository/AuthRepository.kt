package cat.institutmarianao.a1_pbackendalnvol.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class AuthRepository(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    companion object {
        private const val TAG = "AuthRepository"
    }

    val currentUserUid: String?
        get() = auth.currentUser?.uid

    val isUserLoggedIn: Boolean
        get() = auth.currentUser != null

    fun signIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        Log.d(TAG, "Attempting sign in for email: $email")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Sign in successful for uid: ${auth.currentUser?.uid}")
                    onResult(true, null)
                } else {
                    Log.e(TAG, "Sign in failed: ${task.exception?.message}")
                    onResult(false, task.exception?.localizedMessage)
                }
            }
    }

    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        Log.d(TAG, "Attempting sign up for email: $email")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Sign up successful for uid: ${auth.currentUser?.uid}")
                    onResult(true, null)
                } else {
                    Log.e(TAG, "Sign up failed: ${task.exception?.message}")
                    onResult(false, task.exception?.localizedMessage)
                }
            }
    }

    fun signOut() {
        Log.d(TAG, "Signing out user")
        auth.signOut()
    }
}
