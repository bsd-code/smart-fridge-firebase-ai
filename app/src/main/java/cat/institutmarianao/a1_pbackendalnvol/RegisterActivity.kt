package cat.institutmarianao.a1_pbackendalnvol

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cat.institutmarianao.a1_pbackendalnvol.data.repository.AuthRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private val authRepository = AuthRepository()

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    private lateinit var btnRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        btnRegister = findViewById(R.id.btnRegister)
    }

    private fun initListeners() {
        btnRegister.setOnClickListener {
            if (validateInputs()) {
                performRegister()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val email = etEmail.text?.toString()?.trim().orEmpty()
        val password = etPassword.text?.toString()?.trim().orEmpty()
        val confirmPassword = etConfirmPassword.text?.toString()?.trim().orEmpty()

        var isValid = true

        tilEmail.error = null
        tilPassword.error = null
        tilConfirmPassword.error = null

        if (email.isEmpty()) {
            tilEmail.error = "El email no puede estar vacío"
            isValid = false
        }

        if (password.isEmpty()) {
            tilPassword.error = "La contraseña no puede estar vacía"
            isValid = false
        } else if (password.length < 6) {
            tilPassword.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            tilConfirmPassword.error = "Confirma tu contraseña"
            isValid = false
        } else if (password != confirmPassword) {
            tilConfirmPassword.error = "Las contraseñas no coinciden"
            isValid = false
        }

        return isValid
    }

    private fun performRegister() {
        val email = etEmail.text?.toString()?.trim().orEmpty()
        val password = etPassword.text?.toString()?.trim().orEmpty()

        btnRegister.isEnabled = false
        authRepository.signUp(email, password) { success, errorMsg ->
            btnRegister.isEnabled = true
            if (success) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            } else {
                Toast.makeText(
                    this,
                    "Error al registrar: ${errorMsg ?: "Error de registro"}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
