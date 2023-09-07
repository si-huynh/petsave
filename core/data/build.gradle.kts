plugins {
    id("petsave.android.library")
    id("kotlinx-serialization")
}

android {
    namespace = "dev.sihuynh.petsave.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
}