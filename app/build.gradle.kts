plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)

  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.example.taotify"
  compileSdk {
    version = release(36)
  }

  defaultConfig {
    applicationId = "com.example.taotify"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    compose = true
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
  implementation("androidx.navigation:navigation-compose:2.8.5")

  // Retrofit & OkHttp
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
  implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

  // DataStore (session storage)
  implementation("androidx.datastore:datastore-preferences:1.1.1")

  // load and display art cover
  implementation("io.coil-kt:coil-compose:2.6.0")

  implementation("androidx.navigation:navigation-compose:2.9.7")
  implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
  implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")

  implementation("com.google.dagger:hilt-android:2.51.1")
  ksp("com.google.dagger:hilt-android-compiler:2.51.1")

  // Media player
  implementation("androidx.media3:media3-exoplayer:1.10.0")
  implementation("androidx.media3:media3-ui:1.10.0")
  implementation("androidx.media3:media3-common:1.10.0")
  implementation("androidx.media3:media3-ui-compose-material3:1.10.0")
  implementation("androidx.compose.material3:material3:1.5.0-alpha15")
}