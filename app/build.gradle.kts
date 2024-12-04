plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.gms.google-services") // Agrega esta línea
    // Aplicar el plugin de KAPT para Glide
    id("kotlin-kapt")
}

android {
    namespace = "com.example.handsup"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.handsup"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)

    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // Importa el Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    // Agrega las dependencias para los productos de Firebase que deseas usar
    implementation("com.google.firebase:firebase-analytics-ktx") // Ejemplo: Firebase Analytics
    implementation("com.google.firebase:firebase-auth-ktx") // Ejemplo: Firebase Authentication
    implementation("com.google.firebase:firebase-firestore") // Firebase Firestore

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
