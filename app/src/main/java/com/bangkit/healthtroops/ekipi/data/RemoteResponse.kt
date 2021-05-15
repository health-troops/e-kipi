package com.bangkit.healthtroops.ekipi.data

class RemoteResponse private constructor(
    val status: Status,
    val errorMessage: String?,
) {
    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
    }

    companion object {
        fun success() = RemoteResponse(Status.SUCCESS, null)
        fun loading() = RemoteResponse(Status.LOADING, null)
        fun error(message: String) = RemoteResponse(Status.ERROR, message)
    }
}
