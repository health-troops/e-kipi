package com.bangkit.healthtroops.ekipi.data


sealed class Resource<out R> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    private var showed = false

    fun isNotShowed() = !showed
    fun setShowed() {
        showed = true
    }
}
