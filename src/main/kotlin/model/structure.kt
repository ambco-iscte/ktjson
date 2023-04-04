package model

sealed class JSONElement(internal var owner: JSONElement? = null): IAcceptVisitors {
    val depth: Int
        get() = 1 + (owner?.depth ?: -1) + (if (owner is JSONProperty) -1 else 0)
}

data class JSONObject(val properties: List<JSONProperty>): JSONElement() {
    init {
        properties.forEach { it.owner = this }
    }

    companion object {
        val EMPTY: JSONObject = JSONObject(listOf())
    }

    override fun toString(): String = if (properties.isEmpty()) "{ }" else {
        if (owner !is JSONProperty) "${"\t".repeat(depth)}{" +
                "\n${properties.joinToString(",\n")}" +
                "\n${"\t".repeat(depth)}}"
        else "{\n${properties.joinToString(",\n")}\n${"\t".repeat(depth)}}"
    }

    override fun accept(visitor: JSONVisitor) {
        if (visitor.visit(this))
            properties.forEach { it.accept(visitor) }
        visitor.endVisit(this)
    }
}

data class JSONProperty(val key: String, val value: JSONElement): JSONElement() {
    init {
        value.owner = this
    }

    override fun toString(): String =  "\t".repeat(depth) + "\"$key\": $value"

    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
        value.accept(visitor)
    }
}

data class JSONArray(val elements: List<JSONElement>): JSONElement() {
    init {
        elements.forEach { it.owner = this }
    }

    override fun toString(): String = "[\n${elements.joinToString(",\n")}\n${"\t".repeat(depth)}]"

    override fun accept(visitor: JSONVisitor) {
        if (visitor.visit(this))
            elements.forEach { it.accept(visitor) }
        visitor.endVisit(this)
    }
}

internal val preamble: (JSONElement) -> String = { d -> if (d.owner is JSONProperty) "" else "\t".repeat(d.depth) }

data class JSONString(val string: String): JSONElement() {
    override fun toString(): String = preamble(this) + "\"$string\""
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}

data class JSONNumber(val number: Number): JSONElement() {
    override fun toString(): String = preamble(this) + number.toString()
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}

data class JSONBoolean(val boolean: Boolean): JSONElement() {
    override fun toString(): String = preamble(this) + boolean.toString()
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}

object Null: JSONElement() {
    override fun toString(): String = preamble(this) + "null"
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
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
            JSONString("Hello! This is a string. :)")
        )))
    ))
    println(json)
}