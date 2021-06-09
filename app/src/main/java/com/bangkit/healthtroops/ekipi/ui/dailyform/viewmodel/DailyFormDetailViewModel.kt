package com.bangkit.healthtroops.ekipi.ui.dailyform.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.healthtroops.ekipi.data.KipiRepository
import com.bangkit.healthtroops.ekipi.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyFormDetailViewModel @Inject constructor(private val repository: KipiRepository) :
    ViewModel() {

    private val symptomNames = MutableLiveData<Resource<List<String>>>()

    fun fetchSymptomNames(formId: Int) {
        viewModelScope.launch {
            repository.getSymptomNamesByFormId(formId).collectLatest {
                symptomNames.postValue(it)
            }
        }
    }

    fun getSymptomNames(): LiveData<Resource<List<String>>> = symptomNames
}
