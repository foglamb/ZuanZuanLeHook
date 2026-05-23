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
    // AndroidX 支持库 - 用于设置界面和日志界面
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.preference:preference:1.2.1")
}
