package com.bangkit.healthtroops.ekipi.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.FragmentLogInBinding
import com.bangkit.healthtroops.ekipi.model.Account
import com.bangkit.healthtroops.ekipi.model.LoginResponse
import com.bangkit.healthtroops.ekipi.service.remote.AuthService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LogInFragment : Fragment() {
    companion object {
        private const val TAG = "LogInFragment"
    }

    private var binding: FragmentLogInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSignUp?.setOnClickListener {
            view.findNavController().navigate(R.id.action_logInFragment_to_signUpLoginFragment)
        }

        binding?.btnLogIn?.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://causal-tracker-312605.uc.r.appspot.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AuthService::class.java)

            val call = service.logIn(
                Account(
                    binding?.edtEmail?.text.toString(),
                    binding?.edtPassword?.text.toString(),
                )
            )

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.response.size == 1) {
                            view.findNavController()
                                .navigate(R.id.action_logInFragment_to_homeActivity)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "The email or password is incorrect",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "not success: ${response.code()} = ${response.raw()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "onResponse: ${response.raw()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}