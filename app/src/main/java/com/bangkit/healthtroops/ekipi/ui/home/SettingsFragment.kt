package com.bangkit.healthtroops.ekipi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.FragmentSettingsBinding
import com.bangkit.healthtroops.ekipi.domain.model.ItemSelect

class SettingsFragment : Fragment() {

    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            tvFullName.text = "Leonardo" // TODO get from backend

            val items = listOf(
                ItemSelect(
                    getString(R.string.my_profile), getString(R.string.my_profile_desc)
                ) {
                    findNavController().navigate(R.id.action_settingsFragment_to_profileEditActivity)
                },
                ItemSelect(
                    getString(R.string.my_komorbid), getString(R.string.my_komorbid_desc)
                ) {
                    findNavController().navigate(R.id.action_settingsFragment_to_signUpComorbidFragment2)
                },
                ItemSelect(
                    getString(R.string.sign_out), getString(R.string.sign_out_desc)
                ) {
                    // TODO sign out
                },
            )
            rvItemSelect.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ItemSelectAdapter(items)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
