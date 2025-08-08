plugins {
    alias(libs.plugins.newsly.android.library)
    alias(libs.plugins.newsly.android.library.compose)

}

android {
    namespace = "com.instantsystem.app.feature.home"
    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(projects.common.designsystem)
    implementation(projects.common.domain)
    implementation(projects.common.ui)

    implementation(libs.kotlin.collections.immutable)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.ui.test.manifest)
}