package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.ui.dailyform.viewmodel.DailyFormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyFormActivity : AppCompatActivity() {
    private val viewModel by viewModels<DailyFormViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_form_activity)

        viewModel.success.observe(this, {
            if (it) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, Recommendation())
                    .commitNow()
            }
        })
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DailyFormFragment.newInstance())
                .commitNow()
        }
    }

}
