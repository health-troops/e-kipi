package com.bangkit.healthtroops.ekipi.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()
    }

    companion object {
        const val AUTH_EMAIL = "email"
    }
}
