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
    // 使用本地 Xposed API stub（LSPosed 框架运行时提供）
    compileOnly(file("libs/xposed-api-82.jar"))
}
