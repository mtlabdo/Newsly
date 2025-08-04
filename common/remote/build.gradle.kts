plugins {
    alias(libs.plugins.newsly.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.instantsystem.newsly.common.remote"
}

dependencies {

    implementation(projects.common.core)


    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.ktor.serialization.json)


    testImplementation(libs.junit)

}