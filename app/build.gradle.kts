plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.droid.loginsignup"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.droid.loginsignup"
        minSdk = 24
        targetSdk = 35
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.play.services.auth)  // Google Sign-In
    implementation(libs.play.services.ads) // ads
    implementation("com.github.bumptech.glide:glide:4.16.0") // Glide for image loading
    implementation("com.google.code.gson:gson:2.12.1") // Gson for JSON parsing
    implementation ("com.jsibbold:zoomage:1.3.1") // Zoomage for image zooming
    dependencies {
        implementation ("androidx.paging:paging-runtime-ktx:3.1.1")
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
        implementation ("androidx.recyclerview:recyclerview:1.2.1")
        implementation ("androidx.activity:activity-ktx:1.7.2")
        implementation ("androidx.fragment:fragment-ktx:1.6.2")
    }

}
