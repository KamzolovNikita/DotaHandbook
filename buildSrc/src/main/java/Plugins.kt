object Plugins {
    private const val gradle = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    private const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinPlugin}"
    private const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    private const val kotlinXSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinPlugin}"
    private const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"

    @JvmStatic
    fun all() = listOf(
        gradle,
        kotlin,
        safeArgs,
        kotlinXSerialization,
        detekt
    )
}