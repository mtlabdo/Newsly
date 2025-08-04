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

    testImplementation(libs.junit)
}