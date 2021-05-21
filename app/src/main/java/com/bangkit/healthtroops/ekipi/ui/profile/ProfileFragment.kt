package com.bangkit.healthtroops.ekipi.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.ProfileFragmentBinding
import com.bangkit.healthtroops.ekipi.ui.profile.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding.tvName.text = "Leonardo"
        val riwayatAdapter = RiwayatListAdapter()
        binding.rvRiwayat.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = riwayatAdapter
        }

        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_profileEditActivity)
        }
        binding.btnEditKomorbid.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_signUpComorbidFragment2)
        }
    }

}
