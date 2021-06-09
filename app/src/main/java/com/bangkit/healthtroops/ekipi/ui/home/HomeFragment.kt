package com.bangkit.healthtroops.ekipi.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.healthtroops.ekipi.BuildConfig.META_BASE_URL
import com.bangkit.healthtroops.ekipi.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            wbvConditionTypes.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }
            wbvConditionTypes.setInitialScale(250 * wbvConditionTypes.scaleX.toInt())
            wbvConditionTypes.loadUrl("$META_BASE_URL/public/question/016e6dfe-2fd8-47bc-a124-cfa08d113e60")

            wbvTopSymptoms.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }
            wbvTopSymptoms.setInitialScale(250 * wbvTopSymptoms.scaleX.toInt())
            wbvTopSymptoms.loadUrl("$META_BASE_URL/public/question/c699c74e-de90-4ac3-95af-92dc5d7e6800")
        }
    }
}
