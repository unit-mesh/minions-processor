package org.unitmesh.processor.swagger

import com.github.ajalt.clikt.core.CliktCommand
import io.swagger.oas.models.OpenAPI
import io.swagger.parser.OpenAPIParser;
import io.swagger.parser.models.SwaggerParseResult
import org.slf4j.Logger
import org.unitmesh.processor.swagger.converter.Swagger2Processor
import java.io.File
import kotlin.system.exitProcess


fun main(args: Array<String>) = Runner().main(args)
class Runner : CliktCommand(help = "Action Runner") {
    override fun run() {
        logger.info("Runner started")
        val rootDir = ".." + File.separator + ".." + File.separator
        val apisDir = File(rootDir + "datasets" + File.separator + "swagger")
        if (!apisDir.exists()) {
            logger.error("APIs directory not found: ${apisDir.absolutePath}")
            exitProcess(1)
        }

        apisDir.walkTopDown().filter {
            it.isFile && (it.extension == "yaml" || it.extension == "yml" || it.extension == "json")
        }.forEach {
            try {
                val openAPI = Swagger2Processor.fromFile(it)!!

                logger.info("API: ${openAPI.info.title}")
            } catch (e: Exception) {
                logger.error("Failed to parse ${it.absolutePath}", e)
            }
        }
    }


    companion object {
        val logger: Logger = org.slf4j.LoggerFactory.getLogger(Runner::class.java)
    }
}
