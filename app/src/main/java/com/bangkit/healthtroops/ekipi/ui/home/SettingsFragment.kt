package com.bangkit.healthtroops.ekipi.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.databinding.FragmentSettingsBinding
import com.bangkit.healthtroops.ekipi.domain.model.ItemSelect
import com.bangkit.healthtroops.ekipi.ui.auth.AuthActivity
import com.bangkit.healthtroops.ekipi.ui.profileedit.viewmodel.ProfileEditViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    @Inject
    lateinit var sharedPref: SharedPreferences

    private var binding: FragmentSettingsBinding? = null
    private val viewModel by activityViewModels<ProfileEditViewModel>()

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
            viewModel.getProfile(sharedPref.getInt(AuthActivity.AUTH_ID, 0))
            viewModel.userProfile.observe(viewLifecycleOwner) {
                tvFullName.text = it.name
            }

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
                    getString(R.string.sign_out),
                    getString(R.string.sign_out_desc),
                    resources.getColor(R.color.orange_500)
                ) {
                    sharedPref.edit {
                        remove(AuthActivity.AUTH_EMAIL)
                        remove(AuthActivity.AUTH_ID)
                    }
                    val intent = Intent(requireActivity(), AuthActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    startActivity(intent)
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
