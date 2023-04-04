package model

sealed interface IAcceptVisitors {
    fun accept(visitor: JSONVisitor)
}

/**
 * JSON object visitor.
 */
interface JSONVisitor {
    fun visit(obj: JSONObject): Boolean = true
    fun endVisit(obj: JSONObject) {}

    fun visit(array: JSONArray): Boolean = true
    fun endVisit(array: JSONArray) {}

    fun visit(property: JSONProperty) {}

    fun visit(string: JSONString) {}
    fun visit(number: JSONNumber) {}
    fun visit(boolean: JSONBoolean) {}
    fun visit(empty: Null) {}
}

/**
 * Example visitor: collects all values of every property whose key equals the argument.
 */
class CollectByKey(private val key: String) : JSONVisitor {
    val collected: MutableList<JSONElement> = mutableListOf()

    override fun visit(property: JSONProperty) {
        if (property.key == key)
            collected.add(property.value)
    }

    override fun visit(array: JSONArray): Boolean = true

    override fun visit(obj: JSONObject): Boolean = true
}

/**
 * Example visitor: validates that all arrays in the JSON object are type-uniform.
 */
class ArrayValidator : JSONVisitor {
    var valid: Boolean = true

    override fun visit(array: JSONArray): Boolean {
        valid = array.elements.map { it::class }.toSet().size == 1
        return true
    }

    override fun visit(obj: JSONObject): Boolean = true
}

// Testing
fun main() {
    val json = JSONObject(listOf(
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
            JSONString("Hello! This is a string. :)") // Remove this property to make array-valid
        )))
    ))

    val collector = CollectByKey("name")
    json.accept(collector)
    println(collector.collected.joinToString(", "))

    val validator = ArrayValidator()
    json.accept(validator)
    println(validator.valid)
}