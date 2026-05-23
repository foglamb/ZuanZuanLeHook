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
    // Xposed API 以源码 stub 形式包含在项目中，不需要额外依赖
}
