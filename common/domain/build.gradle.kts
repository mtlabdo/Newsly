plugins {
    alias(libs.plugins.newsly.jvm.library)
    alias(libs.plugins.kotlin.serialization)

}


dependencies {

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.kotlin.serialization.json)

    implementation(libs.kotlinx.coroutines.core)
}