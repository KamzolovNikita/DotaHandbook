package com.anti_toxic.dota.core_api

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading<T> : Resource<T>()
    class Error<T>(val throwable: Throwable) : Resource<T>()
    class Empty<T>: Resource<T>()
}

