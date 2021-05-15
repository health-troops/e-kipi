package com.bangkit.healthtroops.ekipi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.Account
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.data.User
import com.bangkit.healthtroops.ekipi.databinding.FragmentSignUpUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpUserFragment : Fragment() {
    companion object {
        private const val TAG = "SignUpUserFragment"
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
            binding.btnSignUp.setOnClickListener {
                val account = Account(
                    id = null,
                    email = arguments?.getString(EXTRA_EMAIL)!!,
                    password = arguments?.getString(EXTRA_PASSWORD)!!,
                )

                val user = User(
                    accountId = 0,
                    name = arguments?.getString(EXTRA_NAME)!!,
                    gender = getGender(view),
                    ttl = binding.edtTtl.text.toString(),
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

            viewModel.getResponse().observe(viewLifecycleOwner) {
                when (it.status) {
                    RemoteResponse.Status.SUCCESS ->
                        view.findNavController()
                            .navigate(R.id.action_signUpUserFragment_to_homeActivity)
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

    private fun getGender(view: View): String {
        binding?.let {
            val genderSelected = it.rgGender.checkedRadioButtonId
            val genderView: RadioButton = view.findViewById(genderSelected)
            return if (genderView.text == getString(R.string.male))
                "laki-laki"
            else
                "perempuan"
        }
        throw NullPointerException("Binding is null")
    }
}
