import model.*
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import parsing.toAbstractTree
import kotlin.test.Test
import kotlin.test.assertEquals

class TestParsing {

    @Test
    fun testParsing() {
        val lexer = JSONLexer(CharStreams.fromFileName("example.json"))
        val parser = JSONParser(CommonTokenStream(lexer))

        assertEquals(JSONObject(listOf(
            JSONProperty("uc", JSONString("pa")),
            JSONProperty("ects", JSONNumber(6.0)),
            JSONProperty("date-exam", Null),
            JSONProperty("students", JSONArray(listOf(
                JSONObject(listOf(
                    JSONProperty("name", JSONString("Afonso")),
                    JSONProperty("number", JSONNumber(92494)),
                    JSONProperty("professor", JSONObject(listOf(
                        JSONProperty("name", JSONString("Andre Santos")),
                        JSONProperty("workingOnStrudel", JSONBoolean(true))
                    )))
                )),
                JSONObject(listOf(
                    JSONProperty("name", JSONString("Gustavo")),
                    JSONProperty("number", JSONNumber(92888))
                )),
                JSONString("Hello! This is a string. :)")
            )))
        )), parser.json().toAbstractTree())
    }
}