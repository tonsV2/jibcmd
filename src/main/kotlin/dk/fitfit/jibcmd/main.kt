package dk.fitfit.jibcmd

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.pair
import com.github.ajalt.clikt.parameters.options.required
import com.google.cloud.tools.jib.api.*
import java.io.File
import java.nio.file.Paths

class Jibcmd : CliktCommand() {
    private val from by option("-f", "--from", help = "Source image").required()
    private val to by option("-t", "--to", help = "Destination image").required()
    private val layers: List<Pair<String, String>> by option("-l", "--layer", help = "Layer... Eg. --layer ./index.html /srv").pair().multiple(required = true)
    private val user by option("-u", "--user", help = "User running the application")
    private val username by option("--reg-user", help = "Registry username")
    private val password by option("--reg-pass", help = "Registry Password")

    override fun run() {
        echo("Building image...")
        echo("From: $from")
        echo("To: $to")
        echo("With layers")
        layers.forEach { echo(" - ${it.first} -> ${it.second}") }

        val builder = Jib.from(from)
        layers.forEach {
            val path = File(it.first)
            val paths = if (path.isDirectory) {
                path.listFiles()?.map { file -> Paths.get(file.toString()) }
            } else {
                listOf(Paths.get(it.first))
            }
            builder.addLayer(paths, AbsoluteUnixPath.get(it.second))
        }
        val containerized = if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            Containerizer.to(RegistryImage.named(to).addCredential(username, password))
        } else {
            Containerizer.to(DockerDaemonImage.named(to))
        }
        builder.setUser(user)
        builder.containerize(containerized)
        echo("Done building image!")
    }
}

object Application {
    @JvmStatic
    fun main(args: Array<String>) = Jibcmd().main(args)
}
