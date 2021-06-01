package com.bangkit.healthtroops.ekipi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.healthtroops.ekipi.data.KipiRepository
import com.bangkit.healthtroops.ekipi.data.Resource
import com.bangkit.healthtroops.ekipi.data.source.remote.response.FormKipiDailyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyFormHistoryViewModel @Inject constructor(
    private val repository: KipiRepository
) : ViewModel() {
    private val formDailyList = MutableLiveData<Resource<List<FormKipiDailyResponse>>>()

    fun fetchData() {
        viewModelScope.launch {
            repository.getFormKipiDaily().collectLatest {
                formDailyList.postValue(it)
            }
        }
    }

    fun getFormDailyList(): LiveData<Resource<List<FormKipiDailyResponse>>> = formDailyList
}
