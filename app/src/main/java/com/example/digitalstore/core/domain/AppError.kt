package com.example.digitalstore.core.domain

sealed class AppError: Exception(){
    object NetworkError : AppError()
    object Unauthorized : AppError()
    object Forbidden : AppError()
    object NotFound : AppError()
    object TimeoutError: AppError()
    data class ServerError(val code: Int) : AppError()
    data class Unknown(val exception: Throwable) : AppError()

    fun toMessageError(): String{
        return when(this){
            is NetworkError -> "No internet connection"
            is Unauthorized -> "Unauthorized access"
            is Forbidden -> "You do not have permission to access this resource"
            is NotFound -> "Resource not found"
            is TimeoutError -> "Connection timed out. Please check your internet"
            is ServerError -> "Internal server error (Code: $code). Please try again"
            is Unknown -> "An unknown error occurred"
        }
    }
}