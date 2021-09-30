import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Versions {
    const val kotlin = "1.4.20"
}

object PluginVersions {
    const val gradle_test_logger = "3.0.0"
    const val ktlint = "10.1.0"
}

object PluginConstants {
    const val artifactId = "gradle-commit"
    const val description = "Gradle plugin which helps to commit & push changes to Git repository"
    const val displayName = "Gradle Git Commit Plugin"
    const val groupId = "ai.digital.gradle.plugins"
    const val repositoryUrl = "https://github.com/acierto/gradle-commit"

    fun getVersion(): String {
        return "1.0.0-${LocalDateTime.now().format(DateTimeFormatter.ofPattern("Mdd.Hmm"))}"
    }
}
