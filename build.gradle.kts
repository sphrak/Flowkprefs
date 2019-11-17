plugins {
    kotlin("jvm") version "1.3.60"
}
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-alpha03")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.60")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }

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
    version = "1.0.1"
    group = "io.github.sphrak"
}