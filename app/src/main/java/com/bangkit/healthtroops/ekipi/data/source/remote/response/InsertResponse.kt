package com.bangkit.healthtroops.ekipi.data.source.remote.response

data class InsertResponse(
    val status: Int?,
    val error: InsertErrorResponse?,
    val response: InsertResponseResult,
)

data class InsertResponseResult(
    val fieldCount: Int,
    val affectedRows: Int,
    val insertId: Int,
    val serverStatus: Int,
    val warningCount: Int,
    val message: String,
    val protocol41: Boolean,
    val changedRows: Int,
)

data class InsertErrorResponse(
    val code: String,
    val sqlMessage: String,
)
