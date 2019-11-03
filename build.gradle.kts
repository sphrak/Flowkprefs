plugins {
    kotlin("jvm") version "1.3.50"
}
buildscript {
    extra["kotlin_version"] = "1.3.60-eap-25"
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-alpha01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
        classpath("com.github.jengelman.gradle.plugins:shadow:5.0.0")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
    version = "1.0.0-alpha03"
    group = "io.github.sphrak"
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }
}