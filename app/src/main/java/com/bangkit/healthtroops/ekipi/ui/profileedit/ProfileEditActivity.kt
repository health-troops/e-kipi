package com.bangkit.healthtroops.ekipi.ui.profileedit

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.UserResponse
import com.bangkit.healthtroops.ekipi.databinding.FragmentSignUpUserBinding
import com.bangkit.healthtroops.ekipi.ui.profileedit.viewmodel.ProfileEditViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileEditActivity : AppCompatActivity() {

    private lateinit var binding: FragmentSignUpUserBinding
    private val viewModel by viewModels<ProfileEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignUpUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSignUp.text = getString(R.string.save)
            tvLabelSignUp.text = getString(R.string.my_profile)
            cLayout.isFocusable = true
            cLayout.isFocusableInTouchMode = true
            lytName.visibility = View.VISIBLE

            lytPlaceOfBirth.clearErrorOnTextChanged()
            lytTtl.clearErrorOnTextChanged()
            lytPhoneNumber.clearErrorOnTextChanged()
            lytMotherSName.clearErrorOnTextChanged()
            lytFatherSName.clearErrorOnTextChanged()
            lytProvince.clearErrorOnTextChanged()
            lytCity.clearErrorOnTextChanged()
            lytDistrict.clearErrorOnTextChanged()
            lytSubDistrict.clearErrorOnTextChanged()
            lytAddress.clearErrorOnTextChanged()
            lytName.clearErrorOnTextChanged()

            btnSignUp.setOnClickListener {
                if (isValid(root)) {
                    val user = UserResponse(
                        accountId = 0,
                        name = edtName.text.toString(),
                        gender = getGender(root)!!,
                        placeOfBirth = edtPlaceOfBirth.text.toString(),
                        ttl = getFormattedDate(),
                        noHp = edtPhoneNumber.text.toString(),
                        mothersName = edtMotherSName.text.toString(),
                        fathersName = edtFatherSName.text.toString(),
                        province = edtProvince.text.toString(),
                        city = edtCity.text.toString(),
                        district = edtDistrict.text.toString(),
                        subDistrict = edtSubDistrict.text.toString(),
                        address = edtAddress.text.toString(),
                    )
                    viewModel.editProfile(user)
                }
            }

        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getProfile()
        viewModel.userProfile.observe(this, {
            if (it != null) {
                with(binding) {
                    edtAddress.setText(it.address)
                    edtCity.setText(it.city)
                    edtDistrict.setText(it.district)
                    edtPlaceOfBirth.setText(it.placeOfBirth)
                    edtTtl.setText(convertDate(it.ttl))
                    edtSubDistrict.setText(it.subDistrict)
                    edtDistrict.setText(it.district)
                    edtProvince.setText(it.province)
                    edtFatherSName.setText(it.fathersName)
                    edtMotherSName.setText(it.mothersName)
                    edtPhoneNumber.setText(it.noHp)
                    edtName.setText(it.name)
                    rgGender.check(
                        when (it.gender) {
                            getString(R.string.laki_laki) -> rgGender.getChildAt(0).id
                            getString(R.string.perempuan) -> rgGender.getChildAt(1).id
                            else -> -1
                        }
                    )
                }
            }
        })

        viewModel.remoteResponse.observe(this) {
            when (it.status) {
                RemoteResponse.Status.SUCCESS -> {
                    finish()
                    Toast.makeText(this, "Edit profile success!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun convertDate(dateStr: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = dateFormat.parse(dateStr)
        return if (date != null) {
            val parser = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            parser.format(date)
        } else {
            ""
        }
    }

    private fun getGender(view: View): String? {
        binding.let {
            val genderSelected = it.rgGender.checkedRadioButtonId
            if (genderSelected == -1) return null

            val genderView: RadioButton = view.findViewById(genderSelected)
            return when (genderView.text.toString()) {
                getString(R.string.male) -> getString(R.string.laki_laki)
                getString(R.string.female) -> getString(R.string.perempuan)
                else -> null
            }
        }
    }

    private fun getFormattedDate(): String {
        val parser = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = parser.parse(binding.edtTtl.text.toString())!!
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(date)
    }

    private fun TextInputLayout.clearErrorOnTextChanged() {
        editText?.addTextChangedListener {
            isErrorEnabled = false
        }
    }

    private fun TextInputLayout.isNotEmpty(): Boolean {
        val input = editText?.text.toString()
        val label = editText?.hint.toString()
        if (input.isEmpty()) {
            error = getString(R.string.error_empty, label)
            return false
        }
        return true
    }

    private fun TextInputLayout.isDate(): Boolean {
        val input = editText?.text.toString()
        val label = editText?.hint.toString()
        try {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formatter.parse(input)
        } catch (e: ParseException) {
            error = getString(R.string.error_date, label, "DD/MM/YYYY")
            return false
        }
        return true
    }

    private fun isValid(view: View): Boolean {
        binding.let { binding ->
            var valid = getGender(view) != null
            valid = binding.lytPlaceOfBirth.isNotEmpty() && valid
            valid = binding.lytTtl.isNotEmpty() && binding.lytTtl.isDate() && valid
            valid = binding.lytPhoneNumber.isNotEmpty() && valid
            valid = binding.lytMotherSName.isNotEmpty() && valid
            valid = binding.lytFatherSName.isNotEmpty() && valid
            valid = binding.lytProvince.isNotEmpty() && valid
            valid = binding.lytCity.isNotEmpty() && valid
            valid = binding.lytDistrict.isNotEmpty() && valid
            valid = binding.lytSubDistrict.isNotEmpty() && valid
            valid = binding.lytAddress.isNotEmpty() && valid

            return valid
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
