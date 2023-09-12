import com.android.build.api.dsl.LibraryExtension
import dev.sihuynh.petsave.configureGradleManagedDevices
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("petsave.android.library")
                apply("petsave.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "dev.sihuynh.petsave.core.testing.PetSaveTestRunner"
                }
                configureGradleManagedDevices(this)
            }
        }
    }
}