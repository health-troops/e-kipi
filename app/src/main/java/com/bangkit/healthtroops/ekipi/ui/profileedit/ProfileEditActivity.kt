package com.bangkit.healthtroops.ekipi.ui.profileedit

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.User
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
    // Change This later
    private val accountId = 2

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
                    val user = User(
                        accountId = accountId,
                        name = edtName.text.toString(),
                        gender = getGender(root)!!,
                        placeOfBirth = edtPlaceOfBirth.text.toString(),
//                        Belom ni
                        ttl = viewModel.userProfile.value?.placeOfBirth ?: "",
                        noHp = edtPhoneNumber.text.toString(),
                        mothersName = edtMotherSName.text.toString(),
                        fathersName = edtFatherSName.text.toString(),
                        province = edtProvince.text.toString(),
                        city = edtCity.text.toString(),
                        district = edtDistrict.text.toString(),
                        subDistrict = edtSubDistrict.text.toString(),
                        address = edtAddress.text.toString(),
                    )
                    viewModel.editProfile(accountId, user)
                }
            }

        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.loading.observe(this, {
            binding.progressCircular.visibility = when (it) {
                true -> View.VISIBLE
                false -> View.GONE
            }

        })
        viewModel.getProfile(accountId)
        viewModel.userProfile.observe(this, {
            if (it != null) {
                with(binding) {
                    progressCircular.visibility = View.GONE
                    edtAddress.setText(it.address)
                    edtCity.setText(it.city)
                    edtDistrict.setText(it.district)
                    edtPlaceOfBirth.setText(it.placeOfBirth)
                    edtSubDistrict.setText(it.subDistrict)
                    edtDistrict.setText(it.district)
                    edtProvince.setText(it.province)
                    edtFatherSName.setText(it.fathersName)
                    edtMotherSName.setText(it.mothersName)
                    edtPhoneNumber.setText(it.noHp)
                    edtName.setText(it.name)
                    rgGender.clearCheck()
                    rgGender.check(when (it.gender) {
                        "laki-laki" -> 1
                        "perempuan" -> 2
                        else -> -1
                    })
                }
            }
        })
    }

    private fun getGender(view: View): String? {
        binding.let {
            val genderSelected = it.rgGender.checkedRadioButtonId
            if (genderSelected == -1) return null

            val genderView: RadioButton = view.findViewById(genderSelected)
            return when (genderView.text.toString()) {
                getString(R.string.male) -> "laki-laki"
                getString(R.string.female) -> "perempuan"
                else -> null
            }
        }
    }

    private fun getFormattedDate(): String {
        val parser = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = parser.parse(binding!!.edtTtl.text.toString())!!
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
}
