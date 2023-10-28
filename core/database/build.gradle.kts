plugins {
    id("petsave.android.library")
    id("petsave.android.library.jacoco")
    id("petsave.android.hilt")
    id("petsave.android.room")
}

android {
    defaultConfig {
        testInstrumentationRunner =
            "dev.sihuynh.petsave.core.testing.PetSaveTestRunner"
    }
    namespace = "dev.sihuynh.petsave.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.androidx.room.paging)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    androidTestImplementation(project(":core:testing"))
}
