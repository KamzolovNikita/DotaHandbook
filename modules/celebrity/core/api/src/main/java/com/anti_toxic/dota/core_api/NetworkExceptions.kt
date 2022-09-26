package com.anti_toxic.dota.core_api

import retrofit2.HttpException
import java.io.IOException

object NetworkExceptions {

    fun isNetworkError(e: Exception): Boolean {
        return e is IOException
    }

    fun isServerError(e: Exception): Boolean {
        return if (e is HttpException) {
            (e.code() % 100) == 5
        } else {
            false
        }
    }

    fun isClientError(e: Exception): Boolean {
        return if (e is HttpException) {
            (e.code() % 100) == 4
        } else {
            false
        }
    }
}