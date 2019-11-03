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
        const val kotlinStdLib = "1.3.50"
        const val kotlinxCoroutinesCore = "1.3.0"
        const val kotlinxCoroutinesAndroid = "1.3.0"
        const val kotlinxCoroutinesTest = "1.3.0"
        const val ktlintPlugin = "0.35.0"
        const val timber = "4.7.1"
        const val junit = "4.12"
        const val androidxConstraintLayout = "2.0.0-beta2"
        const val androidxAppCompat = "1.1.0"
        const val androidxCoreKtx = "1.2.0-alpha04"
        const val androidxAppCompatResources = "1.1.0"
        const val assertK = "0.19"
        const val androidXPreference = "1.1.0"
        const val mockK = "1.9.3"
    }

    object Android {
        const val minSdk = 21
        const val targetSdk = 28
        const val compileSdk = 28
        const val versionCode = 1
        const val versionName = "1.0.0"
    }

    object Library {
        const val assertK = "com.willowtreeapps.assertk:assertk-jvm:${Version.assertK}"
        const val mockK = "io.mockk:mockk:${Version.mockK}"
        const val androidXPreference = "androidx.preference:preference-ktx:${Version.androidXPreference}"

        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlinStdLib}"
        const val androidxCoreKtx = "androidx.core:core-ktx:${Version.androidxCoreKtx}"
        const val timber = "com.jakewharton.timber:timber:${Version.timber}"
        const val ktlintPlugin = "com.pinterest:ktlint:${Version.ktlintPlugin}"

        const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.kotlinxCoroutinesCore}"
        const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.kotlinxCoroutinesAndroid}"
        const val kotlinxCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.kotlinxCoroutinesTest}"
        const val junit = "junit:junit:${Version.junit}"

        const val androidxAppCompat = "androidx.appcompat:appcompat:${Version.androidxAppCompat}"
        const val androidxAppCompatResources = "androidx.appcompat:appcompat-resources:${Version.androidxAppCompatResources}"
        const val androidxConstraintLayout = "androidx.constraintlayout:constraintlayout:${Version.androidxConstraintLayout}"
    }
}
