package dk.fitfit.cmdjib

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.pair
import com.google.cloud.tools.jib.api.*
import java.nio.file.Paths

class Jibcmd : CliktCommand() {
    val from by option("-f", "--from", help = "Source image")
    val to by option("-t", "--to", help = "Destination image")
    val layers: List<Pair<String, String>> by option("-l", "--layer", help = "Layer...").pair().multiple()
    val username by option("-u", "--user", help = "Username")
    val password by option("-p", "--pass", help = "Password")

    override fun run() {
        echo("Building image...")
        echo("From: $from")
        echo("To: $to")
        echo("With layers")
        layers.forEach { echo("${it.first}:${it.second}") }

        val builder = Jib.from(from)
        layers.forEach { builder.addLayer(listOf(Paths.get(it.first)), AbsoluteUnixPath.get(it.second)) }
        val containerized = if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            Containerizer.to(RegistryImage.named(to).addCredential(username, password))
        } else {
            Containerizer.to(DockerDaemonImage.named(to))
        }
        builder.containerize(containerized)
        echo("Done building image!")
    }
}

object Application {
    @JvmStatic
    fun main(args: Array<String>) = Jibcmd().main(args)
}
