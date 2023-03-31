package org.unitmesh.processor.swagger.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class Swagger2ProcessorTest {
    @Test
    fun `should merge by tags`() {
        val openAPI = Swagger2Processor.fromFile(File(javaClass.classLoader.getResource("v2-hello-world.yml").file))!!
        val processor = Swagger2Processor(openAPI)

        val result = processor.mergeByTags()
        assertEquals(1, result.size)


        val expected = """Users
GET /users Returns a list of users.

""".trimIndent()
        assertEquals(expected, ApiDetails.formatApiDetailsByTag(result))
    }
}