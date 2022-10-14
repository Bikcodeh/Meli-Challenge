package com.bikcodeh.melichallenge.domain.common

import com.microsoft.appcenter.crashes.Crashes
import kotlinx.coroutines.*
import retrofit2.Response
import timber.log.Timber

/**
 * Allows to make a request in a safe way catching possible errors
 * and sending report to app center and prints log with timber
 * @return Result<T>: returns a Result wrapper with the given expected data
 */
suspend fun <T : Any> makeSafeRequest(
    execute: suspend () -> Response<T>
): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body)
            } else {
                Result.Error(code = response.code(), message = response.message())
            }
        } catch (e: Exception) {
            Timber.e(e)
            Crashes.trackError(e)
            Result.Exception(e)
        }
    }
}