plugins {
    alias(libs.plugins.newsly.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.instantsystem.newsly.common.data"


}

dependencies {

    implementation(projects.common.core)

    implementation(projects.common.domain)
    implementation(projects.common.remote)

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.truth)
    testImplementation(libs.mockk) }