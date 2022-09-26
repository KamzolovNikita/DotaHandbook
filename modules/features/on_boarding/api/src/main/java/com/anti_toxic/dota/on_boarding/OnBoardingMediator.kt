package com.anti_toxic.dota.on_boarding

import com.anti_toxic.dota.core_api.di.FeatureMediator

interface OnBoardingMediator: FeatureMediator {
    fun openOnBoarding()
}