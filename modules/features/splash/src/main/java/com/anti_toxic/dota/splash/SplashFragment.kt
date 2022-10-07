package com.anti_toxic.dota.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.anti_toxic.dota.core_api.di.ComponentProvider
import com.anti_toxic.dota.core_api.di.ContainerActivityProvider
import com.anti_toxic.dota.main.MainScreenMediator
import kotlinx.coroutines.delay
import javax.inject.Inject

class SplashFragment : Fragment() {

    @Inject
    lateinit var mainScreenMediator: MainScreenMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val componentProvider = activity as ComponentProvider
        DaggerSplashComponent.factory()
            .create(componentProvider.provide(ContainerActivityProvider::class.java))
            .inject(this)

        lifecycleScope.launchWhenStarted {
            delay(1000)
            mainScreenMediator.openMainScreen()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}