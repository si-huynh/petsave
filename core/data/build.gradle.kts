plugins {
    id("petsave.android.library")
    id("petsave.android.library.jacoco")
    id("petsave.android.hilt")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
}

android {
    namespace = "dev.sihuynh.petsave.core.data"
    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
    defaultConfig {
        testInstrumentationRunner =
            "dev.sihuynh.petsave.core.testing.PetSaveTestRunner"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)


    testImplementation(project(":core:datastore-test"))
    androidTestImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.okhttp.mockwebserver)
    androidTestImplementation(libs.retrofit.core)
    androidTestImplementation(libs.retrofit.kotlin.serialization)
    androidTestImplementation(libs.room.runtime)
    androidTestImplementation(libs.room.ktx)
    ksp(libs.room.compiler)
    androidTestImplementation(project(":core:testing"))
}