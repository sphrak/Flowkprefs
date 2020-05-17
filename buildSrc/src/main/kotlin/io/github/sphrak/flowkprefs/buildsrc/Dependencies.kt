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

package io.github.sphrak.flowkprefs.buildsrc

object Dependencies {

    object Version {
        const val kotlinStdLib = "1.3.72"
        const val kotlinxCoroutines = "1.3.5"
        const val ktlintPlugin = "0.36.0"
        const val timber = "4.7.1"
        const val junit = "4.13"
        const val androidxConstraintLayout = "2.0.0-beta2"
        const val androidxAppCompat = "1.1.0"
        const val androidxCoreKtx = "1.2.0-alpha04"
        const val androidxAppCompatResources = "1.1.0"
        const val assertK = "0.19"
        const val androidxPreference = "1.1.0"
        const val mockK = "1.9.3"
    }

    object Classpath {
        const val androidToolsBuildGradle: String = "com.android.tools.build:gradle:3.6.0"
        const val kotlinGradlePlugin: String = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinStdLib}"
        const val gradleBintrayPlugin: String = "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"
    }

    object Android {
        const val minSdk = 21
        const val targetSdk = 29
        const val compileSdk = 29
        const val versionCode = 1
        const val versionName = "2.0.0"
    }

    object Library {
        const val assertK = "com.willowtreeapps.assertk:assertk-jvm:${Version.assertK}"
        const val mockK = "io.mockk:mockk:${Version.mockK}"
        const val androidxPreference = "androidx.preference:preference-ktx:${Version.androidxPreference}"

        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlinStdLib}"
        const val androidxCoreKtx = "androidx.core:core-ktx:${Version.androidxCoreKtx}"
        const val timber = "com.jakewharton.timber:timber:${Version.timber}"
        const val ktlintPlugin = "com.pinterest:ktlint:${Version.ktlintPlugin}"

        const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.kotlinxCoroutines}"
        const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.kotlinxCoroutines}"
        const val kotlinxCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.kotlinxCoroutines}"
        const val junit = "junit:junit:${Version.junit}"

        const val androidxAppCompat = "androidx.appcompat:appcompat:${Version.androidxAppCompat}"
        const val androidxAppCompatResources = "androidx.appcompat:appcompat-resources:${Version.androidxAppCompatResources}"
        const val androidxConstraintLayout = "androidx.constraintlayout:constraintlayout:${Version.androidxConstraintLayout}"
    }
}
