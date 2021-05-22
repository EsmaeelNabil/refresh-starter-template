import java.net.URI

buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Libs.gradle.gradle_plugin)
        classpath(Libs.gradle.kotlin_gradle_plugin)
        classpath(Google.dagger.hilt.android.gradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = URI("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}