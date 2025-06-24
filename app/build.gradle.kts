plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "info.firozansari.stackoverflowapp"
    compileSdk = 35

    defaultConfig {
    
        applicationId = "info.firozansari.stackoverflowapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    // AndroidX
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Lifecycle
    implementation(libs.lifecycle)
    kapt(libs.lifecycle.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.coroutines.adapter)
    implementation(libs.httpLog)


    // Navigation
    implementation(libs.navigation.frag)
    implementation(libs.navigation.ui)

    // Moshi
    implementation(libs.moshi)
    kapt(libs.moshiCodeGen)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Room
    implementation(libs.roomRuntime)
    kapt(libs.roomCompiler)
    implementation(libs.roomKotlinExt)
    //implementation(libs.roomPaging)

    // Unit test  dependencies
//    testImplementation(libs.mockK)
//    testRuntimeOnly(libs.junitVintageEngine)
//    testImplementation(libs.junit5Api)
//    testRuntimeOnly(libs.junit5Engine)
//    testImplementation(libs.junit5Params)
//    testImplementation(libs.extJUnit)
//    testImplementation(libs.kotlinxCoroutinesTest)

}
