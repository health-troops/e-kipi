package com.bangkit.healthtroops.ekipi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.databinding.FragmentLogInBinding
import com.bangkit.healthtroops.ekipi.utils.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {
    companion object {
        private const val TAG = "LogInFragment"
    }

    private var binding: FragmentLogInBinding? = null
    private val viewModel by viewModels<LogInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { binding ->
            binding.btnSignUp.setOnClickListener {
                view.findNavController().navigate(R.id.action_logInFragment_to_signUpLoginFragment)
            }

            binding.btnLogIn.setOnClickListener {
                if (isValid()) {
                    val email = binding.edtEmail.text.toString()
                    val password = binding.edtPassword.text.toString()
                    viewModel.logIn(email, password)
                }
            }

            binding.edtEmail.addTextChangedListener { binding.lytEmail.error = null }
            binding.edtPassword.addTextChangedListener { binding.lytPassword.error = null }

            viewModel.getResponse().observe(requireActivity()) { status ->
                when (status.status) {
                    RemoteResponse.Status.SUCCESS -> {
                        view.findNavController()
                            .navigate(R.id.action_logInFragment_to_homeActivity)
                    }
                    RemoteResponse.Status.ERROR -> {
                        Toast.makeText(requireContext(), status.errorMessage, Toast.LENGTH_LONG).show()
                    }
                    RemoteResponse.Status.LOADING -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun isValid(): Boolean {
        binding?.let { binding ->
            var valid = true

            // validate email
            binding.lytEmail.run {
                val email = binding.edtEmail.text.toString()
                val emailLabel = getString(R.string.email)
                if (email.isEmpty()) {
                    valid = false
                    error = getString(R.string.error_empty, emailLabel)
                } else if (!Validator.isEmailValid(email)) {
                    valid = false
                    error = getString(R.string.error_invalid, emailLabel)
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
                    null
                }
            }

            return valid
        }
        return false
    }
}
