plugins {
    alias(libs.plugins.newsly.android.library)
    alias(libs.plugins.newsly.android.library.compose)
}

android {
    namespace = "com.instantsystem.newsly.common.ui"
}

dependencies {
    implementation(projects.common.designsystem)


    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}