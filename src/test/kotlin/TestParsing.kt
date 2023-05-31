import model.elements.*
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import parsing.toAbstractTree
import kotlin.test.Test
import kotlin.test.assertEquals
import JSONLexer
import JSONParser

class TestParsing {

    @Test
    fun testParsing() {
        val lexer = JSONLexer(CharStreams.fromFileName("example.json"))
        val parser = JSONParser(CommonTokenStream(lexer))

        val json = JSONObject(mutableListOf(
            JSONProperty("uc", JSONString("pa")),
            JSONProperty("ects", JSONNumber(6.0)),
            JSONProperty("date-exam", Null()),
            JSONProperty("students", JSONArray(mutableListOf(
                JSONObject(mutableListOf(
                    JSONProperty("name", JSONString("Afonso")),
                    JSONProperty("number", JSONNumber(92494)),
                    JSONProperty("professor", JSONObject(mutableListOf(
                        JSONProperty("name", JSONString("Andre Santos")),
                        JSONProperty("workingOnStrudel", JSONBoolean(true))
                    )))
                )),
                JSONObject(mutableListOf(
                    JSONProperty("name", JSONString("Gustavo")),
                    JSONProperty("number", JSONNumber(92888))
                )),
                JSONString("Hello! This is a string. :)")
            )))
        ))

        assertEquals(json, parser.document().toAbstractTree())
        assertEquals(json, JSONElement.parse("example.json"))
    }

    @Test
    fun testLiteralParsing() {
        assertEquals(Null(), JSONElement.parseLiteral("null"))
        assertEquals(JSONBoolean(true), JSONElement.parseLiteral("true"))
        assertEquals(JSONBoolean(false), JSONElement.parseLiteral("false"))
        assertEquals(JSONNumber(1), JSONElement.parseLiteral("1"))
        assertEquals(JSONNumber(3.14), JSONElement.parseLiteral("3.14"))
        assertEquals(JSONString("Hello world!"), JSONElement.parseLiteral("Hello world!"))
    }
}