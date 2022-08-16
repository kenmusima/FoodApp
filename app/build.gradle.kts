plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelizePlugin)
    id(BuildPlugins.ktlintPlugin)
    id(BuildPlugins.jacocoAndroid)
    id(BuildPlugins.googleServicesPlugin)
}

jacoco {
    toolVersion = Versions.jacoco
}

android {

    compileSdk = AndroidSdk.compileSdkVersion
    android.buildFeatures.viewBinding = true

    defaultConfig {
        applicationId = "com.foodapp"
        minSdk = AndroidSdk.minSdkVersion
        targetSdk = AndroidSdk.targetSdkVersion
        versionCode = AndroidSdk.versionCode
        versionName = AndroidSdk.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        animationsDisabled = true
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
        implementation(Libraries.appCompat)
        implementation(Libraries.constraintLayout)
        implementation(Libraries.materialComponents)
        implementation(platform(Libraries.firebaseBom))
        implementation(Libraries.firebaseAuth)
        implementation(Libraries.firebaseStore)

        androidTestImplementation(TestLibraries.testRunner)
        androidTestImplementation(TestLibraries.espresso)
        androidTestImplementation(TestLibraries.annotation)

        testImplementation(TestLibraries.junit4)
    }
}
