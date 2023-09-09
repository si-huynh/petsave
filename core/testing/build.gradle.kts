plugins {
    id("petsave.android.library")
    id("petsave.android.hilt")
}

android {
    namespace = "dev.sihuynh.petsave.core.testing"
}

dependencies {
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
    api(libs.hilt.android.testing)
}