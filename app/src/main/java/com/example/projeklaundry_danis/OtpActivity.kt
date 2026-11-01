package com.example.projeklaundry_danis

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projeklaundry_danis.databinding.ActivityOtpBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi View Binding
        binding = ActivityOtpBinding.inflate(layoutInflater)
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

        // Ambil data (Nama, No HP, Username, Password, Email) dari Intent
        val fullName = intent.getStringExtra("EXTRA_FULL_NAME") ?: ""
        val phone = intent.getStringExtra("EXTRA_PHONE") ?: ""
        val username = intent.getStringExtra("EXTRA_USERNAME") ?: ""
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: ""
        val password = intent.getStringExtra("EXTRA_PASSWORD") ?: ""

        // Tampilkan Nomor HP ke layar
        binding.tvPhoneNumber.text = phone.ifEmpty { "-" }

        // Aksi Tombol Verifikasi
        binding.btnVerify.setOnClickListener {
            val otpInput = binding.etOtpCode.text.toString().trim()

            // Cek jika input kode OTP SAMA DENGAN Nomor Handphone
            if (otpInput.isNotEmpty() && (otpInput == phone)) {
                // Simpan data USERNAME dan PASSWORD ke SharedPreferences "UserPref" (MODE_PRIVATE)
                val sharedPref = getSharedPreferences("UserPref", MODE_PRIVATE)
                sharedPref.edit {
                    putString("USERNAME", username)
                    putString("PASSWORD", password)
                    putString("username", username)
                    putString("password", password)
                    putString("FULL_NAME", fullName)
                    putString("PHONE", phone)
                    putString("EMAIL", email)
                }

                // Tampilkan Toast
                Toast.makeText(this, "Registrasi Berhasil! Silakan Login", Toast.LENGTH_SHORT).show()

                // Intent berpindah ke LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                // Jika kode OTP BEDA atau KOSONG: tampilkan pesan error dengan MaterialAlertDialogBuilder
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage("Kode OTP salah, gunakan nomor HP Anda sebagai OTP")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Aksi Kirim Ulang OTP
        binding.tvResendOtp.setOnClickListener {
            Toast.makeText(this, "Kode OTP baru telah dikirim ke $phone", Toast.LENGTH_SHORT).show()
        }
    }
}
