package com.bangkit.healthtroops.ekipi.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.FragmentSignUpLoginBinding

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
            binding.btnSignUpNext.setOnClickListener {
                val name = binding.edtName.text.toString()
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                val confirmPassword = binding.edtConfirmPassword.text.toString()

                if (password == confirmPassword) {
                    val bundle = bundleOf(
                        SignUpUserFragment.EXTRA_NAME to name,
                        SignUpUserFragment.EXTRA_EMAIL to email,
                        SignUpUserFragment.EXTRA_PASSWORD to password
                    )
                    view.findNavController()
                        .navigate(R.id.action_signUpLoginFragment_to_signUpUserFragment, bundle)
                } else {
                    Log.d(TAG, "onViewCreated: $password == $confirmPassword")
                    Toast.makeText(
                        requireContext(),
                        "The password and confirmation password is not the same",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
