package com.anti_toxic.dota.core_api

import android.content.SharedPreferences

interface PreferencesProvider {

    fun create(name: String): SharedPreferences

    fun delete(name: String)
}