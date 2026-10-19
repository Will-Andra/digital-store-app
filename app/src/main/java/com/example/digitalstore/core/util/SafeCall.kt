package com.example.digitalstore.core.util

import com.example.digitalstore.core.domain.AppError
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

inline fun <T> safeCall(action: () -> T): Result<T> {
    return try {
        Result.success(action())
    }
    catch (err: HttpException) {
        val appError = when (err.code()) {
            401 -> AppError.Unauthorized
            403 -> AppError.Forbidden
            404 -> AppError.NotFound
            in 500..599 -> AppError.ServerError(err.code())
            else -> AppError.Unknown(err)
        }
        Result.failure(appError)
    }
    catch (err: SocketTimeoutException) {
            Result.failure(AppError.TimeoutError)
    }
    catch (err: IOException) {
        Result.failure(AppError.NetworkError)
    }
    catch (err: Exception) {
        if (err is java.util.concurrent.CancellationException) throw err

        Result.failure(AppError.Unknown(err))
    }
}