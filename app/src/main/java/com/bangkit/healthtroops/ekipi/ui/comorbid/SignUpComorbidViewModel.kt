package com.bangkit.healthtroops.ekipi.ui.comorbid

import androidx.lifecycle.ViewModel
import com.bangkit.healthtroops.ekipi.network.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpComorbidViewModel @Inject constructor(profileService: ProfileService) : ViewModel() {
}
