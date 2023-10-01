import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

// retrieve Api keys.
val keystoreProperties = rootProject.file("keys.properties")
val props = Properties()
if (keystoreProperties.exists()) {
    props.load(FileInputStream(keystoreProperties))
} else {
    props["ADMIN_USERNAME"] = "none"
    props["ADMIN_PASSWORD"] = "none"
}

android {
    val stringType = "String"
    compileSdkVersion(30)
    buildToolsVersion("30.0.2")

    defaultConfig {
        applicationId = "com.pixiedia.pixicommerce"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        getByName("debug") {
            // you can use this with different values inside buildTypes.
            buildConfigField(stringType, "ADMIN_USERNAME", props["adminUsername"] as String)
            buildConfigField(stringType, "ADMIN_PASSWORD", props["adminPassword"] as String)
            buildConfigField(stringType, "BASE_URL", props["base_url"] as String)
            buildConfigField(stringType, "TOKEN_HEADER_NAME", props["token_header_name"] as String)
            buildConfigField(stringType, "DATABASE_NAME", props["dp_name"] as String)
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

    implementation(COIL)
    implementation(COIL.gif)

    // sharedPrefs alternative
    implementation(Libs.security_crypto)

    // Koin
    implementation(Libs.koin.koin_android)
    implementation(Libs.koin.koin_android_viewModel)
    implementation(Libs.koin.koin_androidx_scope)
    implementation(Libs.koin.koin_android_ext)

    //Loading
    implementation(Libs.aviLoader)

    //Localization
    implementation(Libs.localization)

    // dimensions
    implementation(Libs.dimensions.sdp)
    implementation(Libs.dimensions.ssp)

    //firebase
    implementation(platform(Firebase.bom))
    implementation(Firebase.cloudFirestoreKtx)
    implementation(Firebase.analytics)
    implementation(Firebase.dynamicLinks)

    // Room
    implementation(AndroidX.room.runtime)
    implementation(AndroidX.room.migration)
    implementation(AndroidX.room.ktx)
    kapt(AndroidX.room.compiler)

    // Form Validation
    implementation(Libs.formValidation)

    // webView
    implementation(Libs.web_view)

    //App state
    implementation(Libs.State.appState)

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