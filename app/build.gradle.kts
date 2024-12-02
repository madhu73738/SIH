plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.agro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.agro"
        minSdk = 24
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
        compose = true
    }
}

dependencies {

   

    val compose_version = "1.6.0-alpha08"
    val nav_version = "2.8.4"
    implementation(libs.androidx.navigation.compose.v284)
    implementation(libs.ui)
    implementation("androidx.compose.material:material:$compose_version")
    implementation(libs.ui.tooling.preview)


    dependencies {
        implementation("com.squareup.retrofit2:retrofit:2.9.0") {
            exclude(group = "com.android.support", module = "support-v4")
        }
        implementation("com.squareup.retrofit2:converter-gson:2.9.0") {
            exclude(group = "com.android.support", module = "support-v4")
        }
        implementation("com.squareup.okhttp3:okhttp:4.9.0") {
            exclude(group = "com.android.support", module = "support-v4")
        }
    }




    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.navigation.compose)



}