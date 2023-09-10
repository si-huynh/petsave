plugins {
    id("petsave.android.library")
    id("petsave.android.library.jacoco")
    kotlin("kapt")
}

android {
    namespace = "dev.sihuynh.petsave.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    kapt(libs.hilt.compiler)

    testImplementation(project(":core:testing"))
}