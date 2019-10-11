package io.github.sphrak.flowkprefs.buildsrc

object Dependencies {

    object Version {
        const val assertJ = "3.13.2"
        const val corBind = "1.2.0"
        const val androidBuildToolsGradle = "3.6.0-alpha11"
        const val objectboxPlugin = "2.3.4"
        const val kotlinStdLib = "1.3.50"
        const val kotlinxCoroutinesCore = "1.3.0"
        const val kotlinxCoroutinesAndroid = "1.3.0"
        const val kotlinxCoroutinesTest = "1.3.0"
        const val ktlintPlugin = "0.34.2"
        const val either = "1.0.1"
        const val leakCanary = "1.6.3"
        const val dagger = "2.23.1"
        const val daggerCompiler = "2.23.1"
        const val timber = "4.7.1"
        const val junit = "4.12"
        const val threeABP = "1.2.1"
        const val retrofit = "2.6.1"
        const val moshi = "1.8.0"
        const val moshiAdapter = "1.8.0"

        const val androidxConstraintLayout = "2.0.0-beta2"
        const val androidxAppCompat = "1.1.0"
        const val retrofitConverterMoshi = "2.6.1"
        const val moshiKotlinCodeGen = "1.8.0"
        const val androidxCoreKtx = "1.2.0-alpha04"
        const val androidxFragmentKtx = "1.1.0"
        const val androidMaterial = "1.1.0-alpha10"
        const val androidxAppCompatResources = "1.1.0"
        const val assertK = "0.19"
        const val coil = "0.6.1"
        const val androidxNagivationFragment = "2.2.0-alpha02"
        const val androidxNagivationUi = "2.2.0-alpha02"
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
        const val corBind = "ru.ldralighieri.corbind:corbind:${Version.corBind}"
        const val corBindAppCompat = "ru.ldralighieri.corbind:corbind-appcompat:${Version.corBind}"
        const val corBindCore = "ru.ldralighieri.corbind:corbind-core:${Version.corBind}"
        const val corBindDrawerLayout = "ru.ldralighieri.corbind:corbind-drawerlayout:${Version.corBind}"
        const val corBindLeanBack = "ru.ldralighieri.corbind:corbind-leanback:${Version.corBind}"
        const val corBindRecyclerView = "ru.ldralighieri.corbind:corbind-recyclerview:${Version.corBind}"
        const val corBindSlidingPaneLayout = "ru.ldralighieri.corbind:corbind-slidingpanelayout:${Version.corBind}"
        const val corBindSwipeRefreshLayout = "ru.ldralighieri.corbind:corbind-swiperefreshlayout:${Version.corBind}"
        const val corBindViewPager = "ru.ldralighieri.corbind:corbind-viewpager:${Version.corBind}"
        const val corBindViewPager2 = "ru.ldralighieri.corbind:corbind-viewpager2:${Version.corBind}"
        const val coil = "io.coil-kt:coil:${Version.coil}"

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
        const val androidMaterial = "com.google.android.material:material:${Version.androidMaterial}"
    }
}
