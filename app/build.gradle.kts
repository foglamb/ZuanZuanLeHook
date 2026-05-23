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

    // 排除 stub 目录 - 不让它参与编译打包
    sourceSets {
        getByName("main") {
            java {
                setSrcDirs(listOf("src/main/java"))
            }
        }
    }
}

dependencies {
    // 无需Xposed依赖
}
