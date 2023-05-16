package model

import model.elements.*

/**
 * Generic interface for any objects that accept JSON visitors.
 */
interface IAcceptVisitors {
    fun accept(visitor: JSONVisitor)
}

/**
 * Generic [JSONObject] visitor.
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
 * Example visitor: collects all values of every [JSONProperty] with the specific [key].
 * @property key The [JSONProperty] key.
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
 * Example visitor: validates that all [JSONArray]s in the [JSONObject] are type-uniform.
 */
class ArrayTypeValidator : JSONVisitor {
    var valid: Boolean = true

    override fun visit(array: JSONArray): Boolean {
        valid = array.getElements().map { it::class }.toSet().size == 1
        return true
    }

    override fun visit(obj: JSONObject): Boolean = true
}