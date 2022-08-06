object Plugins {
    private const val gradle = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    private const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinPlugin}"

    @JvmStatic
    fun all() = listOf(
        gradle,
        kotlin
    )
}