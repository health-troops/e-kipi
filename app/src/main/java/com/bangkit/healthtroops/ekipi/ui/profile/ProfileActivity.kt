package com.bangkit.healthtroops.ekipi.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.healthtroops.ekipi.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProfileFragment.newInstance())
                .commitNow()
        }
    }
}
