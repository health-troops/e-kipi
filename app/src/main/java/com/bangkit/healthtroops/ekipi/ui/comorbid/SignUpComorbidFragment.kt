package com.bangkit.healthtroops.ekipi.ui.comorbid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.healthtroops.ekipi.databinding.FragmentSignUpComorbidBinding
import com.bangkit.healthtroops.ekipi.utils.DummyData

class SignUpComorbidFragment : Fragment() {
    private var binding: FragmentSignUpComorbidBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpComorbidBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ComorbidListAdapter()

        binding?.rvComorbid?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
        }

        adapter.setData(DummyData.generateComorbidSymptoms(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
