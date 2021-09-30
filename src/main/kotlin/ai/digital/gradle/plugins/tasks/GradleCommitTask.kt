package ai.digital.gradle.plugins.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.BufferedReader
import java.io.InputStreamReader

abstract class GradleCommitTask : DefaultTask() {

    @get:Input
    @get:Option(option = "message", description = "A message to be printed in the Git message.")
    @get:Optional
    abstract val message: Property<String>

    @get:Input
    @get:Option(
        option = "fileContent",
        description = "In case of specified, command `git add 'fileContent'` will be executed."
    )
    @get:Optional
    abstract val fileContent: Property<String>

    @TaskAction
    fun run() {
        executeCommand("git pull")
        executeCommand("git fetch --tags origin")
        executeCommand(
            """
            if [ -n "$(git status --porcelain)" ]; then
                git add ${fileContent.get()} && git commit -m "${message.get()}" 
            fi
        """.trimMargin()
        )
        executeCommand("git merge --no-edit -Xtheirs -s recursive")
        executeCommand("git push")
    }

    private fun executeCommand(command: String) {
        project.logger.lifecycle("Executing command: $command")

        val process: Process = Runtime.getRuntime().exec(arrayOf("sh", "-c", command))

        val stdInput = BufferedReader(InputStreamReader(process.getInputStream()))
        val stdError = BufferedReader(InputStreamReader(process.getErrorStream()))

        var s: String?
        while (stdInput.readLine().also { s = it } != null) {
            project.logger.lifecycle(s)
        }

        while (stdError.readLine().also { s = it } != null) {
            project.logger.lifecycle(s)
        }
    }
}
