// android/app/build.gradle.kts

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
//    id("com.google.gms.google-services") // ✅ Firebase
}

android {

    namespace = "com.example.yoga_session_app" // ✅ App's namespace
    compileSdk = 35
    ndkVersion = "27.0.12077973"

    defaultConfig {
        applicationId = "com.example.yoga_session_app"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug") // ❗ Use proper signing in release
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

flutter {
    source = "../.."
}

dependencies {
    // ✅ Required for QR code scanning and camera
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.8.2")
}

//// ✅ Firebase plugin applied last
//apply(plugin = "com.google.gms.google-services")





//// android/app/build.gradle.kts
//plugins {
//    id("com.android.application")
//    id("kotlin-android")
//    id("dev.flutter.flutter-gradle-plugin")
//    id("com.google.gms.google-services")
//}
//
//android {
//    namespace = "com.example.shinchan"
//    compileSdk = 35
//    ndkVersion = "27.0.12077973"
//
//    defaultConfig {
//        applicationId = "com.example.shinchan"
//        minSdk = 23
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"
//    }
//
//    buildTypes {
//        getByName("release") {
//            isMinifyEnabled = false
//            isShrinkResources = false // ✅ Avoid conflict error
//            signingConfig = signingConfigs.getByName("debug") // Use real signingConfig in production
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//
//    buildFeatures {
//        viewBinding = true
//    }
//}
//
//flutter {
//    source = "../.."
//}
//
//dependencies {
//    // ✅ Required for FlutterFragmentActivity
//    implementation("androidx.fragment:fragment-ktx:1.6.2")
//    implementation("androidx.activity:activity-ktx:1.8.2")
//}
//
//// ✅ Firebase plugin should be applied at the end
//apply(plugin = "com.google.gms.google-services")
//
//
//
//
////todo 2nd code
////// App-level build.gradle.kts
////
////plugins {
////    id("com.android.application")
////    id("kotlin-android")
////    id("dev.flutter.flutter-gradle-plugin") // Flutter plugin
////    id("com.google.gms.google-services") // Firebase plugin
////}
////
////android {
////    namespace = "com.example.shinchan"
////    compileSdk = 35
////    ndkVersion = "27.0.12077973"
////
////    compileOptions {
////        sourceCompatibility = JavaVersion.VERSION_11
////        targetCompatibility = JavaVersion.VERSION_11
////    }
////
////    kotlinOptions {
////        jvmTarget = JavaVersion.VERSION_11.toString()
////    }
////
////    defaultConfig {
////        applicationId = "com.example.shinchan"
////        minSdk = 23
////        targetSdk = 35
////        versionCode = 1 // Ensure this is properly defined
////        versionName = "1.0" // Define version name explicitly
////    }
////
////    buildTypes {
////        release {
////            signingConfig = signingConfigs.getByName("debug")
////        }
////    }
////}
////
////flutter {
////    source = "../.."
////}
////
////// Apply Firebase services plugin
////apply(plugin = "com.google.gms.google-services")
////
////
//
//
//
//
//
//
//
//// todo 1st code
////plugins {
////    id("com.android.application")
////    id("kotlin-android")
////    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
////    id("dev.flutter.flutter-gradle-plugin")
////}
////
////android {
////    namespace = "com.example.shinchan"
////    compileSdk = flutter.compileSdkVersion
////    ndkVersion = "27.0.12077973"
////
//////    ndkVersion = flutter.ndkVersion
////
////    compileOptions {
////        sourceCompatibility = JavaVersion.VERSION_11
////        targetCompatibility = JavaVersion.VERSION_11
////    }
////
////    kotlinOptions {
////        jvmTarget = JavaVersion.VERSION_11.toString()
////    }
////
////    defaultConfig {
////        // TODO: Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html).
////        applicationId = "com.example.shinchan"
////        // You can update the following values to match your application needs.
////        // For more information, see: https://flutter.dev/to/review-gradle-config.
////        minSdk = flutter.minSdkVersion
////        targetSdk = flutter.targetSdkVersion
////        versionCode = flutter.versionCode
////        versionName = flutter.versionName
////    }
////
////    buildTypes {
////        release {
////            // TODO: Add your own signing config for the release build.
////            // Signing with the debug keys for now, so `flutter run --release` works.
////            signingConfig = signingConfigs.getByName("debug")
////        }
////    }
////}
////
////flutter {
////    source = "../.."
////}
