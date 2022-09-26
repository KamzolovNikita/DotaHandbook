package com.anti_toxic.dota.activity_container

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.anti_toxic.dota.main.MainScreenMediator
import com.anti_toxic.dota.on_boarding.OnBoardingMediator
import javax.inject.Inject

class MainContainerNavigator @Inject constructor(
    private val navController: NavController
): OnBoardingMediator, MainScreenMediator {

    override fun openOnBoarding() {
        navigate(NavMainDirections.actionGlobalToOnBoarding())
    }

    override fun openMainScreen() {
        navigate(NavMainDirections.actionGlobalToMainScreen())
    }

    private fun navigate(direction: NavDirections) {
        navController.navigate(direction)
    }
}