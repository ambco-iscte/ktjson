import dynamic.*
import model.*
import kotlin.test.*

class TestSerialization {

    @Test
    fun testNull() {
        assertEquals(Null, null.serialize())
    }

    @Test
    fun `Only Primitive and String Properties`() {
        data class Student(val name: String, val number: Int)
        val serial = Student("Afonso", 92494).serialize()
        println(serial)
        assertEquals(JSONObject(listOf(
            JSONProperty("name", JSONString("Afonso")),
            JSONProperty("number", JSONNumber(92494))
        )), serial)
    }

    @Test
    fun `With Collection Properties`() {
        data class Curriculum(val subjects: List<String>)
        val serial = Curriculum(listOf("PA", "ELP")).serialize()
        println(serial)
        assertEquals(JSONObject(listOf(
            JSONProperty("subjects", JSONArray(listOf(
                JSONString("PA"),
                JSONString("ELP")
            )))
        )), serial)
    }

    enum class StudentType { REGULAR }
    @Test
    fun `With Enumerator Properties`() {
        data class Student(val studentType: StudentType)
        val serial = Student(StudentType.REGULAR).serialize()
        println(serial)
        assertEquals(JSONObject(listOf(
            JSONProperty("studentType", JSONString("REGULAR"))
        )), serial)
    }

    @Test
    fun `With Map Properties`() {
        data class Student(val grades: Map<String, Int>)
        val serial = Student(mapOf("AED" to 20, "TC" to 20)).serialize()
        println(serial)
        assertEquals(JSONObject(listOf(
            JSONProperty("grades", JSONObject(listOf(
                JSONProperty("AED", JSONNumber(20)),
                JSONProperty("TC", JSONNumber(20))
            )))
        )), serial)
    }

    @Test
    fun `With Nested Object Parameters`() {
        data class CurricularUnit(val name: String, val ects: Int)
        data class Student(val name: String, val favourite: CurricularUnit)
        val serial = Student("Afonso", CurricularUnit("PA", 6)).serialize()
        println(serial)
        assertEquals(JSONObject(listOf(
            JSONProperty("name", JSONString("Afonso")),
            JSONProperty("favourite", JSONObject(listOf(
                JSONProperty("name", JSONString("PA")),
                JSONProperty("ects", JSONNumber(6))
            )))
        )), serial)
    }

    @Test
    fun testAnnotations() {
        data class CurricularUnit(
            @SerializeAs("name") val id: String,
            @DoNotSerialize val code: String,
            @Stringify val ects: Double
        )
        val serial = CurricularUnit("PA", "M4310", 6.0).serialize()
        println(serial)
        assertEquals(JSONObject(listOf(
            JSONProperty("name", JSONString("PA")),
            JSONProperty("ects", JSONString("6.0"))
        )), serial)
    }

    @Test
    fun testEverythingAllAtOnce() {
        data class Student(val name: String, @Stringify val number: Int, @DoNotSerialize val internalID: Int)
        data class Professor(val name: String, val areasOfResearch: List<String>)
        data class CurricularUnit(
            @DoNotSerialize val internalID: String,
            @SerializeAs("name") val identifier: String,
            val coordinator: Professor,
            @SerializeAs("professors") val faculty: Set<Professor>,
            @SerializeAs("students") val studentBody: Set<Student>,
            val program: List<String>
        )

        val uc = CurricularUnit(
            "M4310",
            "PA",
            Professor("André Santos", listOf("Pedagogy", "Programming", "Strudel", "Weird Kotlin Things")),
            setOf(Professor("Probably ChatGPT", listOf("Everything"))),
            setOf(Student("Afonso", 92494, 0)),
            listOf("Kotlin", "Reflection", "Visitors")
        )

        val serial = uc.serialize()
        print(serial)

        assertEquals(JSONObject(listOf(
            JSONProperty("name", JSONString("PA")),
            JSONProperty("coordinator", JSONObject(listOf(
                JSONProperty("name", JSONString("André Santos")),
                JSONProperty("areasOfResearch", JSONArray(listOf(
                    JSONString("Pedagogy"),
                    JSONString("Programming"),
                    JSONString("Strudel"),
                    JSONString("Weird Kotlin Things")
                )))
            ))),
            JSONProperty("professors", JSONArray(listOf(
                JSONObject(listOf(
                    JSONProperty("name", JSONString("Probably ChatGPT")),
                    JSONProperty("areasOfResearch", JSONArray(listOf(JSONString("Everything"))))
                ))
            ))),
            JSONProperty("students", JSONArray(listOf(
                JSONObject(listOf(
                    JSONProperty("name", JSONString("Afonso")),
                    JSONProperty("number", JSONString("92494"))
                ))
            ))),
            JSONProperty("program", JSONArray(listOf(
                JSONString("Kotlin"),
                JSONString("Reflection"),
                JSONString("Visitors")
            )))
        )), serial)
    }
}