
plugins {
    id("petsave.android.feature")
    id("petsave.android.library.jacoco")
}

android {
    namespace = "dev.sihuynh.petsave.feature.animals"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.android.material)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.paging)

    implementation(libs.coil.kt)

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:database"))
    implementation(project(":core:model"))
}