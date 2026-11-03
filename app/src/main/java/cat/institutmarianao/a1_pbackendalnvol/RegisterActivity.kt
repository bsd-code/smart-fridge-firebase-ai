package cat.institutmarianao.a1_pbackendalnvol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

// 1. Declarar variables lateinit para las vistas (tilEmail, tilPassword, tilConfirmPassword, etEmail, etPassword, etConfirmPassword, btnRegister)

// override fun onCreate(...) {
//    setContentView(R.layout.activity_register)
//    1. Llama a initComponents()
//    2. Llama a initListeners()
// }

// private fun initComponents() {
//    Vincula cada variable con su ID usando findViewById
// }

// private fun initListeners() {
//    Al hacer clic en btnRegister:
//    - Ejecuta la función de validación
//    - Si es válido, llama a performRegister()
// }

// private fun validateInputs(): Boolean {
//    - Limpia los errores previos (tilEmail.error = null, etc.)
//    - Comprueba si email está vacío -> tilEmail.error = "..."
//    - Comprueba si password está vacío -> tilPassword.error = "..."
//    - Comprueba si confirmPassword está vacío o no coincide con password -> tilConfirmPassword.error = "..."
//    - Devuelve true si todo es correcto o false si falla alguno
// }

// private fun performRegister() {
//    - Usa FirebaseAuth para crear el usuario (createUserWithEmailAndPassword)
//    - Si tiene éxito: abre MainActivity y cierra la pantalla actual
//    - Si falla: muestra un Toast con el mensaje de error
// }
}