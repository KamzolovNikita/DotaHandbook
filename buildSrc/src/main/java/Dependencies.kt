object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"
}

object Retrofit {
    const val core =  "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val kotlinXConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinXConverter}"
}

object AndroidX {
    const val core = "androidx.core:core-ktx:${Versions.androidXCore}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object Navigation {
    const val ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val fragment = "androidx.navigation:navigation-fragment-ktx::${Versions.navigation}"
}

object Dagger {
    const val core = "com.google.dagger:dagger:${Versions.dagger}"
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
}