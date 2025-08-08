plugins {
    alias(libs.plugins.newsly.android.library)
    alias(libs.plugins.newsly.android.library.compose)
}

android {
    namespace = "com.instantsystem.newsly.common.ui"
}

dependencies {
    implementation(projects.common.designsystem)

    api(projects.common.core)
    api(libs.koin.android)
    api(libs.koin.androidX.compose)

    api(libs.androidx.lifecycle.compose.runtime)
    api(libs.coil.kt.compose)


    api(libs.androidx.lifecycle.viewmodel.android)

}