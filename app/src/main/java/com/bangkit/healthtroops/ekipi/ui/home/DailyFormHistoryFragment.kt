package com.bangkit.healthtroops.ekipi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.FragmentDailyFormHistoryBinding

class DailyFormHistoryFragment : Fragment() {

    private var binding: FragmentDailyFormHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyFormHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            btnAddKipi.setOnClickListener {
                findNavController().navigate(R.id.action_dailyFormHistory_to_dailyFormActivity)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
