package com.bangkit.healthtroops.ekipi.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.databinding.FragmentLogInBinding
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
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                viewModel.logIn(email, password)
            }

            viewModel.getStatus().observe(requireActivity()) { status ->
                if (status.status == RemoteResponse.Status.ERROR) {
                    Toast.makeText(requireContext(), status.errorMessage, Toast.LENGTH_LONG).show()
                } else {
                    view.findNavController()
                        .navigate(R.id.action_logInFragment_to_homeActivity)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}