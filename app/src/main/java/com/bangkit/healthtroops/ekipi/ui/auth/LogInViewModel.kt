package com.bangkit.healthtroops.ekipi.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.healthtroops.ekipi.data.KipiRepository
import com.bangkit.healthtroops.ekipi.data.Resource
import com.bangkit.healthtroops.ekipi.data.source.remote.response.AccountResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val kipiRepository: KipiRepository
) : ViewModel() {

    private val response = MutableLiveData<Resource<AccountResponse>>()

    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            kipiRepository.logIn(
                AccountResponse(null, email, password)
            ).collectLatest {
                Log.d("vm", "logIn: $it")
                response.postValue(it)
            }
        }
    }

    fun getResponse(): LiveData<Resource<AccountResponse>> = response
}
