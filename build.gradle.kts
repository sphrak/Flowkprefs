buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(io.github.sphrak.flowkprefs.buildsrc.Dependencies.Classpath.gradleBintrayPlugin)
        classpath(io.github.sphrak.flowkprefs.buildsrc.Dependencies.Classpath.kotlinGradlePlugin)
        classpath(io.github.sphrak.flowkprefs.buildsrc.Dependencies.Classpath.androidToolsBuildGradle)
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
    }
    version = "2.0.0"
    group = "io.github.sphrak"
}