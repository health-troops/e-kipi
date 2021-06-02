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
    private var binding: DailyFormFragmentBinding? = null

    companion object {
        fun newInstance() = DailyFormFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DailyFormFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            val listAdapter = ChecklistListAdapter()
            rvChecks.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = listAdapter
            }

            btnSave.setOnClickListener {
                val checks = listAdapter.checked
                val desc = edtDesc.text?.toString()
                viewModel.sendData(checks, desc)
            }

            btnMoodPredict.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CameraFragment())
                    .addToBackStack(null)
                    .commit()
            }

            viewModel.getAllChecklist()

            viewModel.moodPrediction.observe(viewLifecycleOwner) {
                tvMoodResult.visibility = View.VISIBLE
                tvMoodResult.text = getString(R.string.mood_predict_result, it)
                question1.text = getString(R.string.question1, it)
            }

            viewModel.checklists.observe(viewLifecycleOwner) {
                listAdapter.setList(it)
            }

            viewModel.loading.observe(viewLifecycleOwner) {
                progressCircular.visibility = when (it) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
            }

            viewModel.error.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
