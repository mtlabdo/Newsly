plugins {
    alias(libs.plugins.newsly.android.library)
    alias(libs.plugins.newsly.android.library.compose)
}

android {
    namespace = "com.instantsystem.newsly.app.common.designsystem"
}

dependencies {

    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material3)


    testImplementation(libs.junit)
}