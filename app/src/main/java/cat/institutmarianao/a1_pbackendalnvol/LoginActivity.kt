package cat.institutmarianao.a1_pbackendalnvol

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    // Campos de entrada (para leer el texto ingresado)
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    // Contenedores (Layouts) de los campos (necesarios para mostrar los mensajes de error .error = "...")
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout

    // Botones
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnSignUp: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
    }

    private fun initListeners() {
        btnLogin.setOnClickListener {
            if (validateInputs()) {
                performLogin()
            }
        }

        btnSignUp.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun validateInputs(): Boolean {
        val email = etEmail.text?.toString()?.trim().orEmpty()
        val password = etPassword.text?.toString()?.trim().orEmpty()

        var isValid = true

        tilEmail.error = null
        tilPassword.error = null

        // Validar Email
        if (email.isEmpty()) {
            tilEmail.error = "El email no puede estar vacío"
            isValid = false
        }

        // Validar Password
        if (password.isEmpty()) {
            tilPassword.error = "La contraseña no puede estar vacía"
            isValid = false
        }

        return isValid
    }

    private fun performLogin() {
        val email = etEmail.text?.toString()?.trim().orEmpty()
        val password = etPassword.text?.toString()?.trim().orEmpty()

        btnLogin.isEnabled = false
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Error al iniciar sesión: ${task.exception?.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
