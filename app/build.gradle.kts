import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20"
}

android {
    namespace = "com.example.alzpal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.alzpal"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }
        val apiKey = localProperties.getProperty("apiKey") ?: ""
        val mapsApiKey = localProperties.getProperty("MAPS_API_KEY") ?: ""
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("samples") {
            initWith(getByName("debug"))
        }
    }

    sourceSets.getByName("samples") {
        java.setSrcDirs(listOf("src/main/java", "src/main/kotlin", "../../samples/src/main/java"))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    viewBinding {
        enable = true
    }

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("androidx.room:room-common:2.4.2")
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.core.ktx)
    annotationProcessor("androidx.room:room-compiler:2.4.2")
    testImplementation("androidx.room:room-testing:2.4.2")
    implementation("com.google.firebase:firebase-crashlytics:18.2.9")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.activity:activity:1.9.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.github.blackfizz:eazegraph:1.2.2@aar")
    implementation("com.jakewharton.timber:timber:4.7.1")
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
    implementation("com.google.guava:guava:33.3.1-android")
    implementation("org.reactivestreams:reactive-streams:1.0.4")
    implementation(platform("androidx.compose:compose-bom:2024.09.02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.navigation:navigation-compose:2.8.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.8.0-alpha02")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
}
