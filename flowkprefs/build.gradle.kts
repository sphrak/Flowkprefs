import io.github.sphrak.flowkprefs.buildsrc.Dependencies

plugins {
    id("com.android.library")
    kotlin("android")
}

configurations {
    ktlint
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

android {
    compileSdkVersion(Dependencies.Android.compileSdk)
    defaultConfig {
        minSdkVersion(Dependencies.Android.minSdk)
        targetSdkVersion(Dependencies.Android.targetSdk)
        versionCode = Dependencies.Android.versionCode
        versionName = Dependencies.Android.versionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

val ktlint: Configuration = configurations.create("ktlint")
val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Dependencies.Library.kotlinStdLib)
    api(Dependencies.Library.kotlinxCoroutinesCore)
    implementation(Dependencies.Library.androidxAppCompat)
    implementation(Dependencies.Library.androidxAppCompatResources)
    implementation(Dependencies.Library.androidxCoreKtx)
    implementation(Dependencies.Library.androidXPreference)

    /**
     *  Testing
     */
    testImplementation(Dependencies.Library.junit)
    testImplementation(Dependencies.Library.assertK)
    testImplementation(Dependencies.Library.kotlinxCoroutinesTest)
    testImplementation(Dependencies.Library.mockito)

    implementation(Dependencies.Library.timber)

    /**
     *  Coroutines
     */
    implementation(Dependencies.Library.kotlinxCoroutinesCore)
    implementation(Dependencies.Library.kotlinxCoroutinesAndroid)

    /**
     *  Linting
     */
    ktlint(Dependencies.Library.ktlintPlugin)

}

tasks {

    val ktlint by creating(JavaExec::class) {
        group = "verification"
        description = "Check Kotlin code style."
        classpath = configurations["ktlint"]
        main = "com.pinterest.ktlint.Main"
        args = listOf("src/**/*.kt")
    }

    "check" {
        dependsOn(ktlint)
    }

    create("ktlintFormat", JavaExec::class) {
        group = "formatting"
        description = "Fix Kotlin code style deviations."
        classpath = configurations["ktlint"]
        main = "com.pinterest.ktlint.Main"
        args = listOf("-F", "src/**/*.kt")
    }
}