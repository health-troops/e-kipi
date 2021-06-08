package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.healthtroops.ekipi.databinding.ActivityDailyFormDetailBinding

class DailyFormDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDailyFormDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyFormDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
