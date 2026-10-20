package com.example.projeklaundry_danis

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projeklaundry_danis.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Aksi Tombol Login dengan validasi 2 Aturan Login & SharedPreferences "UserPref"
        binding.btnLogin.setOnClickListener {
            val usernameInput = binding.etPhone.text.toString().trim()
            val passwordInput = binding.etPassword.text.toString().trim()

            // Ambil data tersimpan dari SharedPreferences "UserPref"
            val userPref = getSharedPreferences("UserPref", MODE_PRIVATE)
            val savedUsername = userPref.getString("USERNAME", userPref.getString("username", null))
            val savedPassword = userPref.getString("PASSWORD", userPref.getString("password", null))

            // Rule 1: Jika inputUsername == inputPassword (dan tidak kosong) -> Boleh Login
            val isRule1Valid = usernameInput.isNotEmpty() && (usernameInput == passwordInput)

            // Rule 2: Jika inputUsername == savedUsername DAN inputPassword == savedPassword (dan tidak null/kosong) -> Boleh Login
            val isRule2Valid = !savedUsername.isNullOrEmpty() &&
                    !savedPassword.isNullOrEmpty() &&
                    (usernameInput == savedUsername) &&
                    (passwordInput == savedPassword)

            if (isRule1Valid || isRule2Valid) {
                // Autentikasi Berhasil: Simpan status login ke SharedPreferences
                val sharedPreferences = getSharedPreferences("LaundryPrefs", MODE_PRIVATE)
                sharedPreferences.edit {
                    putBoolean("isLogin", true)
                }

                Toast.makeText(this, "Berhasil login!", Toast.LENGTH_SHORT).show()

                // Navigasi ke HomeActivity dan hapus history stack login
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                // Jika tidak memenuhi Rule 1 maupun Rule 2 -> Tampilkan error menggunakan MaterialAlertDialogBuilder
                MaterialAlertDialogBuilder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau Password salah")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
