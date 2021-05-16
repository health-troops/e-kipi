package com.bangkit.healthtroops.ekipi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.FragmentSignUpLoginBinding
import com.bangkit.healthtroops.ekipi.utils.Validator
import com.google.android.material.textfield.TextInputLayout

class SignUpLoginFragment : Fragment() {
    companion object {
        private const val TAG = "SignUpLoginFragment"
    }

    private var binding: FragmentSignUpLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { binding ->
            binding.lytName.clearErrorOnTextChanged()
            binding.lytEmail.clearErrorOnTextChanged()
            binding.lytPassword.clearErrorOnTextChanged()
            binding.lytConfirmPassword.clearErrorOnTextChanged()

            binding.btnSignUpNext.setOnClickListener {
                if (isValid()) {
                    val name = binding.edtName.text.toString()
                    val email = binding.edtEmail.text.toString()
                    val password = binding.edtPassword.text.toString()

                    val bundle = bundleOf(
                        SignUpUserFragment.EXTRA_NAME to name,
                        SignUpUserFragment.EXTRA_EMAIL to email,
                        SignUpUserFragment.EXTRA_PASSWORD to password
                    )

                    view.findNavController()
                        .navigate(R.id.action_signUpLoginFragment_to_signUpUserFragment, bundle)
                }
            }
        }
    }

    private fun TextInputLayout.clearErrorOnTextChanged() {
        editText?.addTextChangedListener {
            error = null
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun isValid(): Boolean {
        binding?.let { binding ->
            var valid = true

            // validate name
            binding.lytName.run {
                val name = binding.edtName.text.toString()
                val nameLabel = getString(R.string.name)
                if (name.isEmpty()) {
                    error = getString(R.string.error_empty, nameLabel)
                    valid = false
                } else {
                    error = null
                }
            }

            // validate email
            binding.lytEmail.run {
                val email = binding.edtEmail.text.toString()
                val emailLabel = getString(R.string.email)
                if (email.isEmpty()) {
                    error = getString(R.string.error_empty, emailLabel)
                    valid = false
                } else if (!Validator.isEmailValid(email)) {
                    error = getString(R.string.error_invalid, emailLabel)
                    valid = false
                } else {
                    error = null
                }
            }

            // validate password
            binding.lytPassword.run {
                val password = binding.edtPassword.text.toString()
                val passwordLabel = getString(R.string.password)
                if (password.isEmpty()) {
                    error = getString(R.string.error_empty, passwordLabel)
                    valid = false
                } else {
                    error = null
                }
            }

            // validate confirm password
            binding.lytConfirmPassword.run {
                val password = binding.edtPassword.text.toString()
                val confirmPassword = binding.edtConfirmPassword.text.toString()
                val confirmPasswordLabel = getString(R.string.confirm_password)
                if (confirmPassword.isEmpty()) {
                    error = getString(R.string.error_empty, confirmPasswordLabel)
                    valid = false
                } else if (password != confirmPassword) {
                    error = getString(R.string.error_confirm_password_wrong)
                    valid = false
                } else {
                    error = null
                }
            }

            return valid
        }
        return false
    }
}
