plugins {
    id("petsave.android.library")
    id("petsave.android.library.jacoco")
    id("petsave.android.hilt")
}

android {
    namespace = "dev.sihuynh.petsave.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(project(":core:testing"))
}