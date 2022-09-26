package com.anti_toxic.dota.activity_container

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.anti_toxic.dota.activity_container.di.ContainerActivityComponent
import com.anti_toxic.dota.activity_container.di.DaggerContainerActivityComponent
import com.anti_toxic.dota.core_api.di.ComponentProvider
import com.anti_toxic.dota.core_api.di.ContainerActivityProvider
import com.anti_toxic.dota.core_api.di.CoreProvider

class ContainerActivity : AppCompatActivity(), ComponentProvider {

    private var component: ContainerActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        createComponent()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> provide(klass: Class<T>): T {
        val component = this.component ?: createComponent()
        return if (klass.isAssignableFrom(component::class.java)) {
            component as T
        } else {
            (application as ComponentProvider).provide(klass)
        }
    }

    private fun createComponent(): ContainerActivityComponent {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        return DaggerContainerActivityComponent.factory().create(
            this,
            navController
        ).also {
            component = it
        }
    }
}