package com.bangkit.healthtroops.ekipi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.data.Resource
import com.bangkit.healthtroops.ekipi.databinding.FragmentDailyFormHistoryBinding
import com.bangkit.healthtroops.ekipi.domain.model.ItemSelect
import com.bangkit.healthtroops.ekipi.ui.adapter.ItemSelectAdapter
import com.bangkit.healthtroops.ekipi.ui.dailyform.DailyFormDetailActivity

class DailyFormHistoryFragment : Fragment() {

    private var binding: FragmentDailyFormHistoryBinding? = null
    private val viewModel by activityViewModels<DailyFormHistoryViewModel>()

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

            val itemSelectAdapter = ItemSelectAdapter()
            rvItemSelect.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = itemSelectAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            viewModel.fetchData()
            viewModel.getFormDailyList().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        progressIndicator.hide()
                        var counter = 1
                        it.data.map { formKipiDaily ->
                            val dayNumber = "Hari ke-${counter++}"
                            ItemSelect(
                                name = dayNumber,
                                description = formKipiDaily.lainnya
                            ) {
                                val bundle = bundleOf(
                                    DailyFormDetailActivity.EXTRA_DAY_NUMBER to dayNumber,
                                    DailyFormDetailActivity.EXTRA_FORM_KIPI to formKipiDaily
                                )
                                findNavController().navigate(
                                    R.id.action_to_dailyFormDetailActivity,
                                    bundle
                                )
                            }
                        }.let { itemSelect -> itemSelectAdapter.setData(itemSelect) }
                    }
                    is Resource.Loading -> {
                        progressIndicator.show()
                    }
                    is Resource.Error -> {
                        progressIndicator.hide()
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
