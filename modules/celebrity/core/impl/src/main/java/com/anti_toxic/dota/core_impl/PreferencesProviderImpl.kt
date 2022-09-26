package com.anti_toxic.dota.core_impl

import android.content.Context
import android.content.SharedPreferences
import com.anti_toxic.dota.core_api.PreferencesProvider
import com.anti_toxic.dota.core_api.di.ApplicationContext
import javax.inject.Inject

class PreferencesProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
): PreferencesProvider {

    override fun create(name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    override fun delete(name: String) {
        create(name).edit().clear().apply()
    }
}