import io.grpc.internal.SharedResourceHolder.release

plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "com.joypixels.tools"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.joypixels"
            artifactId = "emoji-toolkit-android"
            version = "8.0.12"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
}
