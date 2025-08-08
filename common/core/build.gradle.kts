plugins {
    alias(libs.plugins.newsly.android.library)
}
android {
    namespace = "com.instantsystem.newsly.common.core"
}


dependencies {


    implementation(libs.kotlinx.coroutines.core)

    api(project.dependencies.platform(libs.koin.bom))
    api(libs.koin.compose)
    api(libs.koin.core)
    implementation(libs.androidx.annotation.jvm)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.truth)

    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.ui.test.manifest)

}