package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bangkit.healthtroops.ekipi.databinding.FragmentRecommendationBinding
import com.bangkit.healthtroops.ekipi.ui.dailyform.viewmodel.DailyFormViewModel

/**
 * A simple [Fragment] subclass.
 */
class Recommendation : Fragment() {
    private val viewModel by activityViewModels<DailyFormViewModel>()
    private lateinit var binding: FragmentRecommendationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recommendation.observe(viewLifecycleOwner, {
            val rekomendasi = it.recommendation.rekomendasi
            binding.recommendation.text = rekomendasi
        })

        binding.btnOke.setOnClickListener {
            requireActivity().finish()
        }
    }

}
