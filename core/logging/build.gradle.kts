plugins {
    id("petsave.android.library")
}

android {
    namespace = "dev.sihuynh.petsave.core.logging"
}

dependencies {
    implementation(libs.timber)
}