package com.anti_toxic.dota.core_factory

import android.content.Context
import com.anti_toxic.dota.core_api.di.CoreProvider
import com.anti_toxic.dota.core_impl.di.CoreComponent

object CoreFactory {

    fun createCoreProvider(context: Context): CoreProvider {
        return CoreComponent.create(context)
    }
}