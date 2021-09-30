package ai.digital.gradle.plugins

import ai.digital.gradle.plugins.tasks.GradleCommitTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.util.GradleVersion

const val TASK_NAME = "commitChanges"

open class GradleCommitPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.checkMinimalSupportedGradleVersion()

        project.tasks.register(TASK_NAME, GradleCommitTask::class.java) {
            if (project.hasProperty("gitMessage")) {
                it.message.set(project.property("gitMessage").toString())
            } else {
                project.logger.lifecycle("Parameter `gitMessage` is not specified, using default value: 'Blank commit message.'")
                it.message.set("Blank commit message.")
            }

            if (project.hasProperty("gitFileContent")) {
                it.fileContent.set(project.property("gitFileContent").toString())
            } else {
                project.logger.lifecycle("Parameter `gitFileContent` is not specified, using default value: '-A'")
                it.fileContent.set("-A")
            }
        }
    }

    private fun Project.checkMinimalSupportedGradleVersion() {
        if (GradleVersion.version(gradle.gradleVersion) < GradleVersion.version(LOWEST_SUPPORTED_GRADLE_VERSION)) {
            throw GradleException(
                "Current version of plugin supports minimal Gradle version: $LOWEST_SUPPORTED_GRADLE_VERSION"
            )
        }
    }

    companion object {
        const val LOWEST_SUPPORTED_GRADLE_VERSION = "6.9.1"
    }
}
