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

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.koin.test.junit4)

    // ✅ Tests Android avec Koin
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.truth)
    // Koin Test pour tests Android
    androidTestImplementation("io.insert-koin:koin-test:3.5.0")
    androidTestImplementation("io.insert-koin:koin-test-junit4:3.5.0")
    androidTestImplementation("io.insert-koin:koin-android-test:3.5.0")

    androidTestImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.android)

}