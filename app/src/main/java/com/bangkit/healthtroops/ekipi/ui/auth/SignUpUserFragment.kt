package com.bangkit.healthtroops.ekipi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.Account
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.data.User
import com.bangkit.healthtroops.ekipi.databinding.FragmentSignUpUserBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SignUpUserFragment : Fragment() {
    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PASSWORD = "extra_password"
    }

    private var binding: FragmentSignUpUserBinding? = null
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { binding ->
            binding.lytPlaceOfBirth.clearErrorOnTextChanged()
            binding.lytTtl.clearErrorOnTextChanged()
            binding.lytPhoneNumber.clearErrorOnTextChanged()
            binding.lytMotherSName.clearErrorOnTextChanged()
            binding.lytFatherSName.clearErrorOnTextChanged()
            binding.lytProvince.clearErrorOnTextChanged()
            binding.lytCity.clearErrorOnTextChanged()
            binding.lytDistrict.clearErrorOnTextChanged()
            binding.lytSubDistrict.clearErrorOnTextChanged()
            binding.lytAddress.clearErrorOnTextChanged()

            binding.btnSignUp.setOnClickListener {
                if (isValid(view)) {
                    val account = Account(
                        id = null,
                        email = arguments?.getString(EXTRA_EMAIL)!!,
                        password = arguments?.getString(EXTRA_PASSWORD)!!,
                    )

                    val user = User(
                        accountId = 0,
                        name = arguments?.getString(EXTRA_NAME)!!,
                        gender = getGender(view)!!,
                        placeOfBirth = binding.edtPlaceOfBirth.text.toString(),
                        ttl = getFormattedDate(),
                        noHp = binding.edtPhoneNumber.text.toString(),
                        mothersName = binding.edtMotherSName.text.toString(),
                        fathersName = binding.edtFatherSName.text.toString(),
                        province = binding.edtProvince.text.toString(),
                        city = binding.edtCity.text.toString(),
                        district = binding.edtDistrict.text.toString(),
                        subDistrict = binding.edtSubDistrict.text.toString(),
                        address = binding.edtAddress.text.toString(),
                    )

                    viewModel.register(user, account)
                }
            }

            viewModel.getResponse().observe(viewLifecycleOwner) {
                when (it.status) {
                    RemoteResponse.Status.SUCCESS ->
                        view.findNavController()
                            .navigate(R.id.action_signUpUserFragment_to_signUpComorbidFragment)
                    RemoteResponse.Status.ERROR ->
                        Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
                    RemoteResponse.Status.LOADING -> {
                        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun getGender(view: View): String? {
        binding?.let {
            val genderSelected = it.rgGender.checkedRadioButtonId
            if (genderSelected == -1) return null

            val genderView: RadioButton = view.findViewById(genderSelected)
            return when (genderView.text.toString()) {
                getString(R.string.male) -> "laki-laki"
                getString(R.string.female) -> "perempuan"
                else -> null
            }
        }
        return null
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
        binding?.let { binding ->
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
        return false
    }
}
