package com.example.projeklaundry_danis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.projeklaundry_danis.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek status login dari SharedPreferences
        val sharedPreferences = getSharedPreferences("LaundryPrefs", MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean("isLogin", false)

        // Tunda selama 1.5 detik (1500 ms) sebelum navigasi
        Handler(Looper.getMainLooper()).postDelayed({
            if (isLogin) {
                // Jika sudah login, langsung ke HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                // Jika belum login, ke LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 1500)
    }
}
