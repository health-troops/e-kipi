package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
