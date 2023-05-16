import model.ArrayTypeValidator
import model.CollectByKey
import model.elements.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestModel {

    private val jsonWithEverything = JSONObject(mutableListOf(
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
            ))
        ))
        )
    ))

    private val jsonInvalidArray = JSONObject(mutableListOf(
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

    private val emptyJson = JSONObject(mutableListOf())

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
    fun testObjectManipulation() {
        val obj = JSONObject.empty()

        assertFalse(obj.hasProperty("myKey"))

        obj.addProperty("myKey", JSONNumber(0))
        assertTrue(obj.hasProperty("myKey"))
        assertEquals(JSONNumber(0), obj.getProperty("myKey"))

        obj.setProperty("myKey", JSONBoolean(true))
        assertEquals(JSONBoolean(true), obj.getProperty("myKey"))

        obj.removeProperty("myKey")
        assertFalse(obj.hasProperty("myKey"))
    }

    @Test
    fun testArrayManipulation() {
        val array = JSONArray.empty()

        assertFalse(array.contains(JSONNumber(0)))

        array.add(JSONNumber(0))
        assertTrue(array.contains(JSONNumber(0)))
        assertEquals(array.get(0), JSONNumber(0))

        array.set(0, JSONBoolean(true))
        assertEquals(array.get(0), JSONBoolean(true))

        array.replace(JSONBoolean(true), JSONNumber(1))
        assertEquals(JSONNumber(1), array.get(0))

        array.removeAt(0)
        assertFalse(array.contains(JSONBoolean(true)))
    }

    @Test
    fun testFindAncestor() {
        val nestedProperty = JSONProperty("number", JSONNumber(0))
        val nestedObject = JSONObject(mutableListOf(nestedProperty))
        val property = JSONProperty("boolean", JSONBoolean(true))
        val root = JSONObject(mutableListOf(property, JSONProperty("obj", nestedObject)))

        assertEquals(nestedObject, nestedProperty.findAncestor(JSONObject::class))
        assertEquals(root, property.findAncestor(JSONObject::class))
        assertEquals(root, nestedProperty.findAncestor(JSONObject::class) { it.owner == null })
        assertEquals(null, root.findAncestor(JSONObject::class))
    }

    @Test
    fun `JSON Visitors`() {
        val collector = CollectByKey("name")
        jsonWithEverything.accept(collector)
        assertEquals(listOf("\"Afonso\"", "\"Andre Santos\"", "\"Gustavo\""), collector.collected.map { it.toString() })

        val collector2 = CollectByKey("ThisKeyDoesNotExistAtAllAndItsSoLongThatItsGettingSilly")
        emptyJson.accept(collector2)
        assertEquals(listOf(), collector2.collected.map { it.toString() })

        val validator = ArrayTypeValidator()
        jsonWithEverything.accept(validator)
        assertTrue(validator.valid)

        val validator2 = ArrayTypeValidator()
        emptyJson.accept(validator2)
        assertTrue(validator2.valid)

        val invalidator = ArrayTypeValidator()
        jsonInvalidArray.accept(invalidator)
        assertFalse(invalidator.valid)
    }
}