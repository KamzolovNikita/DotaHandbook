package com.anti_toxic.dota.core_api

import dagger.Reusable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface AppDispatchers {
  fun io(): CoroutineDispatcher = Dispatchers.IO
  fun main(): CoroutineDispatcher = Dispatchers.Main
  fun computation(): CoroutineDispatcher = Dispatchers.Default
}

@Reusable
class ActualAppDispatchers @Inject constructor() : AppDispatchers