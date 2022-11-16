object Versions {

    //Version codes for all the libraries
    const val kotlin = "1.6.10"
    const val appCompat = "1.4.1"
    const val constraintLayout = "2.1.3"
    const val ktx = "1.7.0"
    const val material = "1.5.0"
    const val firebaseBom = "30.3.1"
    const val dataStore = "1.0.0-alpha08"
    const val googlePlayService = "20.2.0"
    const val nav_version = "2.5.1"
    const val glide = "4.13.2"
    const val room = "2.4.3"
    const val hilt = "2.38.1"

    //Version codes for all the test libraries
    const val junit4 = "4.13.2"
    const val testRunner = "1.4.1-alpha03"
    const val espresso = "3.5.0-alpha03"
    const val annotation = "1.4.0-alpha02"

    // Gradle Plugins
    const val ktlint = "10.2.1"
    const val detekt = "1.19.0"
    const val spotless = "6.2.2"
    const val dokka = "1.6.10"
    const val gradleVersionsPlugin = "0.42.0"
    const val jacoco = "0.8.7"
}

object BuildPlugins {
    //All the build plugins are added here
    const val androidLibrary = "com.android.library"
    const val ktlintPlugin = "org.jlleitschuh.gradle.ktlint"
    const val detektPlugin = "io.gitlab.arturbosch.detekt"
    const val spotlessPlugin = "com.diffplug.spotless"
    const val dokkaPlugin = "org.jetbrains.dokka"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlinParcelizePlugin = "org.jetbrains.kotlin.plugin.parcelize"
    const val gradleVersionsPlugin = "com.github.ben-manes.versions"
    const val jacocoAndroid = "com.hiya.jacoco-android"
    const val googleServicesPlugin = "com.google.gms.google-services"
    const val hilt = "dagger.hilt.android.plugin"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
}

object Libraries {
    //Any Library is added here
    const val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val materialComponents = "com.google.android.material:material:${Versions.material}"
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    const val firebaseStore = "com.google.firebase:firebase-firestore-ktx"
    const val datastore = "androidx.datastore:datastore-preferences:${Versions.dataStore}"
    const val googlePlayServ = "com.google.android.gms:play-services-auth:${Versions.googlePlayService}"
    const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val navUI = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideAnnotation = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomAnnotation ="androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
}

object TestLibraries {
    //any test library is added here
    const val junit4 = "junit:junit:${Versions.junit4}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val annotation = "androidx.annotation:annotation:${Versions.annotation}"
}


object AndroidSdk {
    const val minSdkVersion = 21
    const val compileSdkVersion = 31
    const val targetSdkVersion = compileSdkVersion
    const val versionCode = 1
    const val versionName = "1.0"
}