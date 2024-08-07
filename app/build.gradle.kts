plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  
  kotlin("plugin.serialization")
  alias(libs.plugins.compose.compiler)
}

android {
  namespace = "io.oculosdanilo.wimage"
  compileSdk = 34
  
  defaultConfig {
    applicationId = "io.oculosdanilo.wimage"
    minSdk = 31
    targetSdk = 35
    versionCode = 1
    versionName = "1.0.0"
    
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
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
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
  }
  kotlinOptions {
    jvmTarget = "19"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.14"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  
  androidResources {
    @Suppress("UnstableApiUsage")
    generateLocaleConfig = true
  }
}

dependencies {
  
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.fontawesomecompose)
  implementation(libs.androidx.navigation.compose)
  
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.activity)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.navigation.ui.ktx)
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.androidx.material.icons.extended)
  implementation(libs.androidx.datastore.preferences)
  implementation(libs.androidx.fragment)
  implementation(libs.accompanist.permissions)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}