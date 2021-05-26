package com.bangkit.healthtroops.ekipi.ui.comorbid

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.healthtroops.ekipi.data.RemoteResponse
import com.bangkit.healthtroops.ekipi.databinding.FragmentSignUpComorbidBinding
import com.bangkit.healthtroops.ekipi.domain.model.ComorbidData
import com.bangkit.healthtroops.ekipi.ui.auth.AuthActivity
import com.bangkit.healthtroops.ekipi.ui.home.HomeActivity
import com.bangkit.healthtroops.ekipi.utils.DummyData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpComorbidFragment : Fragment() {
    @Inject
    lateinit var sharedPref: SharedPreferences

    private var binding: FragmentSignUpComorbidBinding? = null
    private val viewModel by viewModels<SignUpComorbidViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpComorbidBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ComorbidListAdapter().apply {
            setData(DummyData.generateComorbidSymptoms(requireContext()))
        }

        viewModel.getComorbid()
        viewModel.getComorbidResponse().observe(viewLifecycleOwner) {
            adapter.setValues(it)
        }

        binding?.apply {
            rvComorbid.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                this.adapter = adapter
            }

            btnSave.setOnClickListener {
                val accountId = sharedPref.getInt(AuthActivity.AUTH_ID, 0)
                viewModel.saveComorbid(
                    ComorbidData(
                        accountId,
                        adapter.values[0],
                        adapter.values[1],
                        adapter.values[2],
                        adapter.values[3],
                        adapter.values[4],
                        adapter.values[5],
                        adapter.values[6],
                        adapter.values[7],
                        adapter.values[8],
                        adapter.values[9],
                        adapter.values[10],
                        adapter.values[11],
                        adapter.values[12],
                        adapter.values[13],
                        adapter.values[14],
                        adapter.values[15],
                        adapter.values[16],
                        adapter.values[17],
                        adapter.values[18],
                        adapter.values[19],
                    )
                )
            }

            btnSkip.setOnClickListener {
                navigateToHome()
            }
        }

        viewModel.getRemoteResponse().observe(viewLifecycleOwner) {
            when (it.status) {
                RemoteResponse.Status.SUCCESS -> {
                    navigateToHome()
                }
                else -> {
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun navigateToHome() {
        val intent = Intent(requireActivity(), HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }
}
