package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.healthtroops.ekipi.R
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

        binding.btnSave.setOnClickListener {
            val checks = listAdapter.checked
            val desc = binding.edtDesc.text?.toString()
            viewModel.sendData(checks, desc)
        }

        binding.btnMoodPredict.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, CameraFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.moodPrediction.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.question1.text = "Mengapa anda merasa ${it} hari ini?"
            }
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

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.moodPrediction.observe(viewLifecycleOwner) {
            binding.tvMoodResult.visibility = View.VISIBLE
            binding.tvMoodResult.text = it
        }
    }
}
