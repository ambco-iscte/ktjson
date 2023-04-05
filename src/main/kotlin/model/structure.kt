package model

/**
 * Generic JSON element.
 * @property owner The "owner" of this element. Useful for prettifying the print of JSON elements.
 */
sealed class JSONElement(internal var owner: JSONElement? = null): IAcceptVisitors {
    val depth: Int
        get() = 1 + (owner?.depth ?: -1) + (if (owner is JSONProperty) -1 else 0)
}

/**
 * JSON object.
 * @property properties A list of JSON properties, i.e. key-value pairs.
 */
data class JSONObject(val properties: List<JSONProperty>): JSONElement() {
    init {
        properties.forEach { it.owner = this }
    }

    companion object {
        /**
         * JSON object with no properties.
         */
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

/**
 * JSON object property.
 * @property key The name of the property.
 * @property value The value of the property. Can be any JSON element.
 */
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

/**
 * JSON array. Can contain any JSON elements.
 * @property elements A list of the JSON elements contained in the JSON array.
 */
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

/**
 * JSON string.
 * @property string The string held by this JSON element.
 */
data class JSONString(val string: String): JSONElement() {
    override fun toString(): String = preamble(this) + "\"$string\""
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}

/**
 * JSON number.
 * @property number The number value held by this JSON element.
 */
data class JSONNumber(val number: Number): JSONElement() {
    override fun toString(): String = preamble(this) + number.toString()
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}

/**
 * JSON boolean.
 * @property boolean The boolean value held by this JSON element.
 */
data class JSONBoolean(val boolean: Boolean): JSONElement() {
    override fun toString(): String = preamble(this) + boolean.toString()
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}

/**
 * Null JSON element.
 */
object Null: JSONElement() {
    override fun toString(): String = preamble(this) + "null"
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}