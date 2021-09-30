import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class NebulaRelease : DefaultTask() {

    @TaskAction
    fun doRelease() {
        val version = project.version
        project.logger.lifecycle("Releasing version is: $version")

        project.exec {
            executable("./gradlew")
            args(
                "build", "uploadArchives", "-Prelease.version=$version", "final",
                "-Prelease.ignoreSuppliedVersionVerification=true"
            )
        }
    }
}
