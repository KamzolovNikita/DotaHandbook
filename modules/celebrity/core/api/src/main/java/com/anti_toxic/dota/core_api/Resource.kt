package com.anti_toxic.dota.core_api

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading<T> : Resource<T>()
    class Error<T>(val throwable: Throwable? = null) : Resource<T>()
    class Empty<T> : Resource<T>()

    // assume that no one will try to compare resources with different generic types
    override fun equals(other: Any?): Boolean {
        return if (this is Success && other is Success<*>) {
            this.data == other.data
        } else if (this is Error && other is Error<*>) {
            this.throwable == other.throwable
        } else {
            this.javaClass == other?.javaClass
        }
    }
}

