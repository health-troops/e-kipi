package com.bangkit.healthtroops.ekipi.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bangkit.healthtroops.ekipi.BuildConfig.BASE_URL
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.FragmentSignUpUserBinding
import com.bangkit.healthtroops.ekipi.data.Account
import com.bangkit.healthtroops.ekipi.data.InsertResponse
import com.bangkit.healthtroops.ekipi.data.User
import com.bangkit.healthtroops.ekipi.network.AuthService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpUserFragment : Fragment() {
    companion object {
        private const val TAG = "SignUpUserFragment"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PASSWORD = "extra_password"
    }

    private var binding: FragmentSignUpUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSignUp?.setOnClickListener {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            val service = retrofit.create(AuthService::class.java)
            registerAccount(view, service)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun registerAccount(view: View, service: AuthService) {
        val call = service.registerAccount(
            Account(
                id = null,
                email = arguments?.getString(EXTRA_EMAIL)!!,
                password = arguments?.getString(EXTRA_PASSWORD)!!,
            )
        )

        call.enqueue(object : Callback<InsertResponse> {
            override fun onResponse(
                call: Call<InsertResponse>,
                response: retrofit2.Response<InsertResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()!!.response.insertId}")
                    registerUser(view, service, response.body()!!.response.insertId)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "not success: ${response.code()} = ${response.raw()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onResponse: ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<InsertResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerUser(view: View, service: AuthService, accountId: Int) {
        binding?.let { binding ->
            val gender = getGender(view)
            val call = service.registerUser(
                User(
                    accountId = accountId,
                    name = arguments?.getString(EXTRA_NAME)!!,
                    gender = gender,
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
            )

            call.enqueue(object : Callback<InsertResponse> {
                override fun onResponse(
                    call: Call<InsertResponse>,
                    response: retrofit2.Response<InsertResponse>
                ) {
                    if (response.isSuccessful) {
                        view.findNavController()
                            .navigate(R.id.action_signUpUserFragment_to_homeActivity)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "not success: ${response.code()} = ${response.raw()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "onResponse: ${response.raw()}")
                    }
                }

                override fun onFailure(call: Call<InsertResponse>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
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