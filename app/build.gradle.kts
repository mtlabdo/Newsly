import java.util.Properties

plugins {
    alias(libs.plugins.newsly.android.application)
    alias(libs.plugins.newsly.android.application.compose)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val apiKey: String = localProperties.getProperty("API_KEY") ?: "####NO_API_KEY####"



android {
    namespace = "com.instantsystem.newsly"

    defaultConfig {
        applicationId = "com.instantsystem.newsly"

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"$apiKey\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.add("/META-INF/LICENSE.md")
            excludes.add("/META-INF/LICENSE-notice.md")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(projects.common.ui)
    implementation(projects.common.data)
    implementation(projects.common.domain)
    implementation(projects.common.core)

    implementation(projects.appFeature.home)
    implementation(projects.appFeature.detail)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)

    implementation(libs.kotlinx.serialization.core)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.compose)
    implementation(libs.koin.android)

    implementation(libs.androidx.splashscreen)

    implementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.truth)

    androidTestImplementation(libs.androidx.ui.test.junit4)
}

