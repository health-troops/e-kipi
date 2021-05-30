package com.bangkit.healthtroops.ekipi.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.healthtroops.ekipi.data.KipiRepository
import com.bangkit.healthtroops.ekipi.data.Resource
import com.bangkit.healthtroops.ekipi.data.source.remote.response.AccountResponse
import com.bangkit.healthtroops.ekipi.data.source.remote.response.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val kipiRepository: KipiRepository
) : ViewModel() {

    private val remoteResponse = MutableLiveData<Resource<Boolean>>()

    fun getResponse(): LiveData<Resource<Boolean>> = remoteResponse

    fun register(user: UserResponse, accountResponse: AccountResponse) {
        viewModelScope.launch {
            kipiRepository.register(user, accountResponse).collectLatest {
                remoteResponse.postValue(it)
            }
        }
    }
}
