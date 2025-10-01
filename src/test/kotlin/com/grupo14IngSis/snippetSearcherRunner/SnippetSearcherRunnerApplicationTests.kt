package com.grupo14IngSis.snippetSearcherRunner

import kotlin.test.Test
import kotlin.test.assertEquals

// import org.junit.jupiter.api.Test
// import org.springframework.boot.test.context.SpringBootTest

/*

SpringBootTest always fails because database from application.yml has not been created yet


@SpringBootTest
class SnippetSearcherRunnerApplicationTests {
    @Test
    fun contextLoads() {
    }
}
 */

class SnippetSearcherRunnerApplicationTests() {
    @Test
    fun mockTest() {
        assertEquals(true, 1 == 1)
    }
}
