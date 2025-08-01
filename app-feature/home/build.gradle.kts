plugins {
    alias(libs.plugins.newsly.android.library)
    alias(libs.plugins.newsly.android.library.compose)

}

android {
    namespace = "com.instantsystem.app.feature.home"
}

dependencies {

    implementation(projects.appCommon.designsystem)
    implementation(projects.appCommon.ui)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}