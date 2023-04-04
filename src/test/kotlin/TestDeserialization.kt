import dynamic.deserialize
import dynamic.serialize
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDeserialization {

    @Test
    fun `Only Primitive and String Properties`() {
        data class Student(val name: String, val number: Int)
        val student = Student("Afonso", 92494)
        assertEquals(student, Student::class.deserialize(serialize(student)))
    }

    @Test
    fun `With Collection Properties`() {
        data class Curriculum(val subjects: List<String>)
        val curriculum = Curriculum(listOf("PA", "ELP"))
        assertEquals(curriculum, Curriculum::class.deserialize(serialize(curriculum)))
    }

    enum class StudentType { REGULAR, INTERNATIONAL }
    @Test
    fun `With Enumerator Properties`() {
        data class Student(val studentType: StudentType)
        val student = Student(StudentType.REGULAR)
        assertEquals(student, Student::class.deserialize(serialize(student)))
    }

    @Test
    fun `With Map Properties`() {
        data class Student(val grades: Map<String, Int>)
        val student = Student(mapOf("AED" to 20, "TC" to 20))
        assertEquals(student, Student::class.deserialize(serialize(student)))
    }

    @Test
    fun `With Nested Object Parameters`() {
        data class CurricularUnit(val name: String, val ects: Int)
        data class Student(val name: String, val favourite: CurricularUnit)
        val student = Student("Afonso", CurricularUnit("PA", 6))
        assertEquals(student, Student::class.deserialize(serialize(student)))
    }

    @Test
    fun testEverything() {
        data class Student(val name: String, val number: Int, val studentType: StudentType, val internalID: Int)
        data class Professor(val name: String, val areasOfResearch: List<String>)
        data class CurricularUnit(
            val internalID: String,
            val identifier: String,
            val coordinator: Professor,
            val faculty: Set<Professor>,
            val studentBody: Set<Student>,
            val program: List<String>
        )

        val uc = CurricularUnit(
            "M4310",
            "PA",
            Professor("André Santos", listOf("Pedagogy", "Programming", "Strudel", "Weird Kotlin Things")),
            setOf(Professor("Probably ChatGPT", listOf("Everything"))),
            setOf(Student("Afonso", 92494, StudentType.INTERNATIONAL, 0)),
            listOf("Kotlin", "Reflection", "Visitors")
        )

        assertEquals(uc, CurricularUnit::class.deserialize(serialize(uc)))
    }
}