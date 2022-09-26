package com.anti_toxic.dota.core_api

class OneShotEvent<T>(private val value: T) {

    private var isHandled: Boolean = false
        get() = field.also {
            field = true
        }
        set(value) {}

    fun getValue(force: Boolean = false): T? {
        return if (force || !isHandled) {
            value
        } else {
            null
        }
    }
}