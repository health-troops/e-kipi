package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.healthtroops.ekipi.databinding.DailyFormFragmentBinding
import com.bangkit.healthtroops.ekipi.ui.dailyform.viewmodel.DailyFormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyFormFragment : Fragment() {
    private val viewModel by activityViewModels<DailyFormViewModel>()
    private lateinit var binding: DailyFormFragmentBinding

    companion object {
        fun newInstance() = DailyFormFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DailyFormFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = ChecklistListAdapter()
        binding.rvChecks.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = listAdapter
        }

        binding.cancelButton.setOnClickListener {
            requireActivity().finish()
        }

        binding.btnSave.setOnClickListener {
            val checks = listAdapter.checked
            val desc = binding.edtDesc.text?.toString()
            viewModel.sendData(checks, desc)
        }

        viewModel.checklists.observe(viewLifecycleOwner) {
            listAdapter.setList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressCircular.visibility = when (it) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        }

        viewModel.getAllChecklist()
    }

}
