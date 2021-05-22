import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "9.4.0"
    id("com.google.secrets_gradle_plugin") version "0.6"
}

// 2. Optionally configure the plugin
secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "secrets.properties"
}

android {
    val stringType = "String"
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.esmaeel.usecases"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        getByName("debug") {
            // you can use this with different values inside buildTypes.
            isDebuggable = true
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        // We have to add the explicit cast before accessing the options itself.
        val options = this as org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
        options.jvmTarget = "1.8"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
    }

    bundle {
        language {
            // We want to be able to switch the locale at runtime using AppLocale!
            enableSplit = false
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }
}

dependencies {


    implementation(Kotlin.stdlib.jdk8)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.activityKtx)
    implementation(AndroidX.activity)
    implementation(AndroidX.fragmentKtx)
    implementation(AndroidX.fragment)
    implementation(Google.android.material)
    implementation(AndroidX.constraintLayout)

    implementation(Square.okHttp3.okHttp)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation(Square.retrofit2.retrofit)
    implementation(Square.retrofit2.converter.gson)

    implementation(JakeWharton.timber)

    implementation(AndroidX.lifecycle.runtime)
    implementation(AndroidX.lifecycle.viewModelKtx)
    implementation(AndroidX.lifecycle.liveDataKtx)

    implementation(AndroidX.recyclerView)
    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)
    implementation(KotlinX.coroutines.core)
    implementation(KotlinX.coroutines.android)
    implementation(KotlinX.coroutines.playServices)

    implementation(Libs.formValidation)
    implementation(COIL)

    //Loading
    implementation(Libs.aviLoader)

    //Localization
    implementation(Libs.localization)

    // dimensions
    implementation(Libs.dimensions.sdp)
    implementation(Libs.dimensions.ssp)

    // Room
    implementation(AndroidX.room.runtime)
    implementation(AndroidX.room.migration)
    implementation(AndroidX.room.ktx)
    kapt(AndroidX.room.compiler)


    // Dagger-Hilt
    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.android.compiler)
    testImplementation(Google.dagger.hilt.android.testing)

    // Testing
    implementation(AndroidX.test.core)
    implementation(AndroidX.test.coreKtx)
    implementation(AndroidX.test.ext.junit)
    implementation(AndroidX.test.ext.junitKtx)
    implementation(AndroidX.test.runner)
    implementation(AndroidX.test.rules)
    androidTestImplementation(Libs.test.kaspresso)
    androidTestImplementation(Libs.test.kotest)


}