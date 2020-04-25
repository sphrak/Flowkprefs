/*
 * Copyright 2019 Niclas Kron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    implementation(project(":flowkprefs"))
    implementation(Dependencies.Library.kotlinStdLib)
    implementation(Dependencies.Library.kotlinxCoroutinesCore)
    implementation(Dependencies.Library.androidxAppCompat)
    implementation(Dependencies.Library.androidxConstraintLayout)
    implementation(Dependencies.Library.timber)

}