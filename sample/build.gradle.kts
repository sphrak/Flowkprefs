import io.github.sphrak.flowkprefs.buildsrc.Dependencies

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(Dependencies.Android.compileSdk)
    defaultConfig {
        applicationId = "io.github.sphrak.flowkprefs.sample"
        minSdkVersion(Dependencies.Android.minSdk)
        targetSdkVersion(Dependencies.Android.targetSdk)
        versionCode = Dependencies.Android.versionCode
        versionName = Dependencies.Android.versionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    lintOptions.isAbortOnError = false

    androidExtensions {
        isExperimental = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    api(project(":flowkprefs"))
    implementation(Dependencies.Library.kotlinStdLib)
    implementation(Dependencies.Library.androidxAppCompat)
    implementation(Dependencies.Library.androidxConstraintLayout)
    implementation(Dependencies.Library.timber)

}