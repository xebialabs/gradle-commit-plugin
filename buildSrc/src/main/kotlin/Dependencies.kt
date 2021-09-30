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
    const val groupId = "com.xebialabs.gradle.plugins"
    const val repositoryScm = "https://github.com/xebialabs/gradle-commit-plugin"
    const val repositoryLicense = "${repositoryScm}/blob/master/LICENSE"
    const val repositoryUrl = "${repositoryScm}.git"

    fun getVersion(): String {
        return "1.0.0-${LocalDateTime.now().format(DateTimeFormatter.ofPattern("Mdd.Hmm"))}"
    }
}
