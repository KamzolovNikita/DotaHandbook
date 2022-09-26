package com.anti_toxic.dota.core_api

interface NetworkCache<T> {
    fun fetch(): T
}