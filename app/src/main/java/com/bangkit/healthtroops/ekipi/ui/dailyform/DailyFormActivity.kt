package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.healthtroops.ekipi.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_form_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DailyFormFragment.newInstance())
                .commitNow()
        }
    }
}
