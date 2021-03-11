import java.net.URI

buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Libs.gradle.gradle_plugin)
        classpath(Libs.gradle.kotlin_gradle_plugin)
        classpath(Libs.gradle.safe_args_gradle_plugin)
        classpath(Libs.services.google_services)
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