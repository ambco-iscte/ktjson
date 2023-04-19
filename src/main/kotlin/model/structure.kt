package model

/**
 * Generic JSON element.
 * @property owner The "owner" of this element. Useful for prettifying the print of JSON elements.
 */
sealed class JSONElement(internal var owner: JSONElement? = null): IAcceptVisitors {
    internal val depth: Int
        get() = 1 + (owner?.depth ?: -1) + (if (owner is JSONProperty) -1 else 0)
}

/**
 * JSON object.
 * @property properties A list of JSON properties, i.e. key-value pairs.
 */
data class JSONObject(val properties: MutableList<JSONProperty>): JSONElement() {
    init {
        properties.forEach { it.owner = this }
    }

    companion object {
        /**
         * JSON object with no [JSONProperty]s.
         */
        val EMPTY: JSONObject = JSONObject(mutableListOf())
    }

    /**
     * Does the object have a [JSONProperty] with the given [key]?
     * @param key The JSON property key.
     * @return True if any of the object's properties match the given key; False, otherwise.
     */
    fun hasProperty(key: String): Boolean = properties.any { it.key == key }

    /**
     * Add a new [JSONProperty] to the object.
     * @param key The [JSONProperty] key.
     * @param value The [JSONProperty] value.
     */
    fun addProperty(key: String, value: JSONElement) = properties.add(JSONProperty(key, value))

    /**
     * Returns the [JSONElement] value of this object's [key] [JSONProperty].
     * @param key The [JSONProperty] key.
     */
    fun getProperty(key: String): JSONElement = properties.first { it.key == key }.value

    /**
     * Sets a [JSONProperty] on this object. If the property with the given [key] is not present, it is added.
     * @param key The [JSONProperty] key.
     * @param value The [JSONProperty] value.
     */
    fun setProperty(key: String, value: JSONElement) {
        if (hasProperty(key)) properties[properties.indexOfFirst { it.key == key }] = JSONProperty(key, value)
        else addProperty(key, value)
    }

    /**
     * Removes the [JSONProperty] with the given [key].
     * @param key The [JSONProperty] key.
     */
    fun removeProperty(key: String) {
        if (!hasProperty(key)) return
        properties.removeAt(properties.indexOfFirst { it.key == key })
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
 * @property value The value of the property. Can be any [JSONElement].
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
 * JSON array. Can contain any [JSONElement]s.
 * @property elements A [MutableList] of the [JSONElement]s contained in the array.
 */
data class JSONArray(val elements: MutableList<JSONElement>): JSONElement() {
    init {
        elements.forEach { it.owner = this }
    }

    /**
     * The number of [JSONElement]s in the array.
     */
    val length: Int = elements.size

    /**
     * Adds a [JSONElement] to the array.
     * @param element Any [JSONElement], excluding [JSONProperty]s.
     */
    fun add(element: JSONElement) {
        if (element is JSONProperty)
            throw IllegalStateException("Cannot add JSON properties to an array!")
        elements.add(element)
    }

    /**
     * Gets the [JSONElement] at the given index.
     * @param index A valid array index.
     */
    fun get(index: Int): JSONElement = elements[index]

    /**
     * Is the given [JSONElement] in the array?
     * @param element A [JSONElement].
     * @return True if the given [JSONElement] is in the array; False, otherwise.
     */
    fun contains(element: JSONElement): Boolean = elements.contains(element)

    /**
     * Sets the array [JSONElement] at the given index.
     * @param index A valid array index.
     * @param element A [JSONElement].
     */
    fun set(index: Int, element: JSONElement) {
        if (element is JSONProperty)
            throw IllegalStateException("Cannot add JSON properties to an array!")
        elements[index] = element
    }

    /**
     * Removes and returns the [JSONElement] from the position [index].
     * @param index The index to remove from.
     */
    fun removeAt(index: Int): JSONElement = elements.removeAt(index)

    /**
     * Removes a [JSONElement] from the array.
     * @param element The element to remove.
     */
    fun remove(element: JSONElement) = elements.remove(element)

    override fun toString(): String = "[\n${elements.joinToString(",\n")}\n${"\t".repeat(depth)}]"

    override fun accept(visitor: JSONVisitor) {
        if (visitor.visit(this))
            elements.forEach { it.accept(visitor) }
        visitor.endVisit(this)
    }
}

// Pretty indentation :)
internal val preamble: (JSONElement) -> String = { d -> if (d.owner is JSONProperty) "" else "\t".repeat(d.depth) }

/**
 * JSON string.
 * @property string The string held by this [JSONElement].
 */
data class JSONString(val string: String): JSONElement() {
    override fun toString(): String = preamble(this) + "\"$string\""
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}

/**
 * JSON number.
 * @property number The number value held by this [JSONElement].
 */
data class JSONNumber(val number: Number): JSONElement() {
    override fun toString(): String = preamble(this) + number.toString()
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}

/**
 * JSON boolean.
 * @property boolean The boolean value held by this [JSONElement].
 */
data class JSONBoolean(val boolean: Boolean): JSONElement() {
    override fun toString(): String = preamble(this) + boolean.toString()
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}

/**
 * Null [JSONElement].
 */
object Null: JSONElement() {
    override fun toString(): String = preamble(this) + "null"
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}