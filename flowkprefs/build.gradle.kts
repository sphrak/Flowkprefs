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
import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    id("com.android.library")
    `maven-publish`
    id("com.jfrog.bintray")
    kotlin("android")
}

val artifactId = "flowkprefs"

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
    testImplementation(Dependencies.Library.mockK)

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

publishing {
    publications {
        create<MavenPublication>("Flowkprefs") {
            artifact("$buildDir/outputs/aar/flowkprefs-release.aar")
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_API_KEY")
    publish = true
    setPublications("Flowkprefs")
    pkg(
        delegateClosureOf<BintrayExtension.PackageConfig> {
            repo = "Flowkprefs"
            name = artifactId
            vcsUrl = "https://github.com/sphrak/Flowkprefs/"
            version(
                delegateClosureOf<BintrayExtension.VersionConfig> {
                    name = project.version as String
                }
            )
        }
    )
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