plugins {
    alias(libs.plugins.newsly.android.library)
    alias(libs.plugins.newsly.android.library.compose)

}

android {
    namespace = "com.instantsystem.app.feature.home"
}

dependencies {

    implementation(projects.common.designsystem)
    implementation(projects.common.ui)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}