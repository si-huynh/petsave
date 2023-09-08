plugins {
    id("petsave.android.library")
    id("petsave.android.hilt")
}

android {
    namespace = "dev.sihuynh.petsave.core.datastore.test"
}

dependencies {
    api(project(":core:datastore"))
    api(libs.androidx.dataStore.core)

    implementation(libs.protobuf.kotlin.lite)
    implementation(project(":core:common"))
    implementation(project(":core:testing"))
}