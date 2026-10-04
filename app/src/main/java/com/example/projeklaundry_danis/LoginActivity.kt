package com.example.projeklaundry_danis

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projeklaundry_danis.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle edge-to-edge window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Toolbar & Back Navigation
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Aksi Tombol Masuk
        binding.btnLogin.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Nomor ponsel dan kata sandi harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Berhasil masuk!", Toast.LENGTH_SHORT).show()
                // TODO: Lanjutkan ke Dashboard / Home Activity
            }
        }

        // Aksi Tombol Masuk dengan Google
        binding.btnGoogleLogin.setOnClickListener {
            Toast.makeText(this, "Fitur Masuk dengan Google belum tersedia", Toast.LENGTH_SHORT).show()
        }

        // Aksi Lupa Kata Sandi
        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Fitur reset kata sandi", Toast.LENGTH_SHORT).show()
        }

        // Aksi Tautan Daftar Sekarang -> Menuju RegisterActivity
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
