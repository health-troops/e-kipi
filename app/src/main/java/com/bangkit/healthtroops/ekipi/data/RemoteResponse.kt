package com.bangkit.healthtroops.ekipi.data

class RemoteResponse private constructor(
    val status: Status,
    val errorMessage: String?,
) {
    enum class Status {
        SUCCESS,
        ERROR,
    }

    companion object {
        fun success() = RemoteResponse(Status.SUCCESS, null)
        fun error(message: String) = RemoteResponse(Status.ERROR, message)
    }
}
