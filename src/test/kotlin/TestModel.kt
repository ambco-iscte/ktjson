import model.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestModel {

    private val jsonWithEverything = JSONObject(listOf(
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
            ))
        ))
        )
    ))

    private val jsonInvalidArray = JSONObject(listOf(
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
    ))

    private val emptyJson = JSONObject(listOf())

    @Test
    fun `Print JSON Model as String`() {
        val str1 = "{\n" +
                "\t\"uc\": \"pa\",\n" +
                "\t\"ects\": 6.0,\n" +
                "\t\"date-exam\": null,\n" +
                "\t\"students\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Afonso\",\n" +
                "\t\t\t\"number\": 92494,\n" +
                "\t\t\t\"professor\": {\n" +
                "\t\t\t\t\"name\": \"Andre Santos\",\n" +
                "\t\t\t\t\"workingOnStrudel\": true\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Gustavo\",\n" +
                "\t\t\t\"number\": 92888\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}"
        assertEquals(str1, jsonWithEverything.toString())

        val str2 = "{ }"
        assertEquals(str2, emptyJson.toString())

        val str3 = "{\n" +
                "\t\"uc\": \"pa\",\n" +
                "\t\"ects\": 6.0,\n" +
                "\t\"date-exam\": null,\n" +
                "\t\"students\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Afonso\",\n" +
                "\t\t\t\"number\": 92494,\n" +
                "\t\t\t\"professor\": {\n" +
                "\t\t\t\t\"name\": \"Andre Santos\",\n" +
                "\t\t\t\t\"workingOnStrudel\": true\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"Gustavo\",\n" +
                "\t\t\t\"number\": 92888\n" +
                "\t\t},\n" +
                "\t\t\"Hello! This is a string. :)\"\n" +
                "\t]\n" +
                "}"
        assertEquals(str3, jsonInvalidArray.toString())
    }

    @Test
    fun `JSON Visitors`() {
        val collector = CollectByKey("name")
        jsonWithEverything.accept(collector)
        assertEquals(listOf("\"Afonso\"", "\"Andre Santos\"", "\"Gustavo\""), collector.collected.map { it.toString() })

        val collector2 = CollectByKey("ThisKeyDoesNotExistAtAllAndItsSoLongThatItsGettingSilly")
        emptyJson.accept(collector2)
        assertEquals(listOf(), collector2.collected.map { it.toString() })

        val validator = ArrayValidator()
        jsonWithEverything.accept(validator)
        assertTrue(validator.valid)

        val validator2 = ArrayValidator()
        emptyJson.accept(validator2)
        assertTrue(validator2.valid)

        val invalidator = ArrayValidator()
        jsonInvalidArray.accept(invalidator)
        assertFalse(invalidator.valid)
    }
}