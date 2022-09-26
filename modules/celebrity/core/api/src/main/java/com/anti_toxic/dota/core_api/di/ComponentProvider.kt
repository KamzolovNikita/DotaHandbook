package com.anti_toxic.dota.core_api.di

interface ComponentProvider {
    fun <T> provide(klass: Class<T>): T
}