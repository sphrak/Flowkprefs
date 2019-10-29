package io.github.sphrak.flowkprefs.buildsrc

object Dependencies {

    object Version {
        const val assertJ = "3.13.2"
        const val kotlinStdLib = "1.3.50"
        const val kotlinxCoroutinesCore = "1.3.0"
        const val kotlinxCoroutinesAndroid = "1.3.0"
        const val kotlinxCoroutinesTest = "1.3.0"
        const val ktlintPlugin = "0.34.2"
        const val timber = "4.7.1"
        const val junit = "4.12"
        const val androidxConstraintLayout = "2.0.0-beta2"
        const val androidxAppCompat = "1.1.0"
        const val androidxCoreKtx = "1.2.0-alpha04"
        const val androidxAppCompatResources = "1.1.0"
        const val assertK = "0.19"
        const val androidXPreference = "1.1.0"
        const val mockK = "1.9.3"
        const val mockito = "3.1.0"
    }

    object Android {
        const val minSdk = 21
        const val targetSdk = 29
        const val compileSdk = 29
        const val versionCode = 1
        const val versionName = "1.0.0"
    }

    object Library {
        const val assertK = "com.willowtreeapps.assertk:assertk-jvm:${Version.assertK}"
        const val assertJ = "org.assertj:assertj-core:${Version.assertJ}"
        const val mockK = "io.mockk:mockk:${Version.mockK}"
        const val androidXPreference = "androidx.preference:preference-ktx:${Version.androidXPreference}"

        const val mockito = "org.mockito:mockito-core:${Version.mockito}"
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
