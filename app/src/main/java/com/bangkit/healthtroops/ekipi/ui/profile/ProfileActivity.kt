package com.bangkit.healthtroops.ekipi.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.ActivityProfileBinding
import com.bangkit.healthtroops.ekipi.domain.model.ItemDetail
import com.bangkit.healthtroops.ekipi.ui.adapter.ItemDetailAdapter
import com.bangkit.healthtroops.ekipi.ui.profileedit.ProfileEditActivity
import com.bangkit.healthtroops.ekipi.ui.profileedit.viewmodel.ProfileEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel by viewModels<ProfileEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val itemDetailAdapter = ItemDetailAdapter()

        binding.rvProfileDetail.apply {
            adapter = itemDetailAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.fab.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            startActivity(intent)
        }

        viewModel.getProfile()
        viewModel.userProfile.observe(this) {
            itemDetailAdapter.setData(
                listOf(
                    ItemDetail(
                        getString(R.string.name),
                        it.name
                    ),
                    ItemDetail(
                        getString(R.string.gender),
                        it.gender
                    ),
                    ItemDetail(
                        getString(R.string.place_of_birth),
                        it.placeOfBirth
                    ),
                    ItemDetail(
                        getString(R.string.date_of_birth),
                        it.ttl
                    ),
                    ItemDetail(
                        getString(R.string.phone_number),
                        it.noHp
                    ),
                    ItemDetail(
                        getString(R.string.mother_s_name),
                        it.mothersName
                    ),
                    ItemDetail(
                        getString(R.string.father_s_name),
                        it.fathersName
                    ),
                    ItemDetail(
                        getString(R.string.province),
                        it.province
                    ),
                    ItemDetail(
                        getString(R.string.city),
                        it.city
                    ),
                    ItemDetail(
                        getString(R.string.district),
                        it.district
                    ),
                    ItemDetail(
                        getString(R.string.sub_district),
                        it.subDistrict
                    ),
                    ItemDetail(
                        getString(R.string.address),
                        it.address
                    ),
                )
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
