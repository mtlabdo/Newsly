plugins {
    alias(libs.plugins.newsly.android.library)
    alias(libs.plugins.newsly.android.library.compose)
}

android {
    namespace = "com.instantsystem.newsly.app.common.ui"
}

dependencies {
    implementation(projects.appCommon.designsystem)


    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}