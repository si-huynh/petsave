plugins {
    id("petsave.android.library")
    id("petsave.android.library.jacoco")
    id("petsave.android.hilt")
    id("petsave.android.room")
}

android {
//    defaultConfig {
//        testInstrumentationRunner =
//            "com.google.samples.apps.petsave.core.testing.NiaTestRunner"
//    }
    namespace = "dev.sihuynh.petsave.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    androidTestImplementation(project(":core:testing"))
}
