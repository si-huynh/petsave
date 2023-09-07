import com.android.build.api.dsl.TestExtension
import dev.sihuynh.petsave.configureGradleManagedDevices
import dev.sihuynh.petsave.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                configureGradleManagedDevices(this)
            }
        }
    }
}