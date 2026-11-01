plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.org.sabedoria.saotomasdeaquino"
    compileSdk = 35

    defaultConfig {
        applicationId = "br.org.sabedoria.saotomasdeaquino"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    signingConfigs {
        create("release") {
            // Chave de assinatura pré-gerada e incluída na raiz do projeto:
            storeFile = file("../saotomas-release-key.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "saotomas1274"
            keyAlias = System.getenv("KEY_ALIAS") ?: "saotomas_mestre"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "saotomas1274"
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.webkit:webkit:1.11.0")
}
