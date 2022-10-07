package com.anti_toxic.dota.core_api

import com.anti_toxic.dota.core_api.NetworkExceptions.isNetworkError
import com.anti_toxic.dota.core_api.NetworkExceptions.isServerError
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay


fun Exception.rethrowOnCancellation() {
    if (this is CancellationException) {
        throw this
    }
}

suspend fun <T> retryOnNetworkAndServerErrors(
    maxRetry: Int = 3,
    delayMs: Long = 2_000,
    block: suspend () -> T
): T {
    try {
        return block.invoke()
    } catch (e: Exception) {
        if (maxRetry <= 0 || isServerError(e) || isNetworkError(e)) {
            throw e
        }
    }
    delay(delayMs)
    return retryOnNetworkAndServerErrors(maxRetry - 1, delayMs, block)
}