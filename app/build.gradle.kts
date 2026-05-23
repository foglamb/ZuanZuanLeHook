plugins {
    id("com.android.application") version "8.2.2"
}

android {
    namespace = "com.zuanzuanle.hook"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zuanzuanle.hook"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
    compileOnly("de.robv.android.xposed:api:82:sources")
    implementation("org.json:json:20231013")
}
