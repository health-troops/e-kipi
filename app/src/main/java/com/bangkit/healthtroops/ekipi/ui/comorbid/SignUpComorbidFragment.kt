package com.bangkit.healthtroops.ekipi.ui.comorbid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.healthtroops.ekipi.databinding.FragmentSignUpComorbidBinding
import com.bangkit.healthtroops.ekipi.ui.home.HomeActivity
import com.bangkit.healthtroops.ekipi.utils.DummyData

class SignUpComorbidFragment : Fragment() {
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

        binding?.apply {
            rvComorbid.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                this.adapter = adapter
            }

            btnSkip.setOnClickListener {
                navigateToHome()
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
