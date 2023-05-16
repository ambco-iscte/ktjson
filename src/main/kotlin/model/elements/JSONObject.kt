package model.elements

import model.JSONVisitor
import model.listeners.JSONObjectListener

/**
 * JSON object.
 * @property properties A list of JSON properties, i.e. key-value pairs.
 */
data class JSONObject(private val properties: MutableList<JSONProperty>): JSONElement() {
    private val listeners: MutableList<JSONObjectListener> = mutableListOf()

    init {
        properties.forEach { it.owner = this }
    }

    companion object {
        /**
         * JSON object with no [JSONProperty]s.
         */
        fun empty(): JSONObject = JSONObject(mutableListOf())
    }

    fun addListener(listener: JSONObjectListener) = listeners.add(listener)

    fun removeListener(listener: JSONObjectListener) = listeners.remove(listener)

    /**
     * Returns a [List] of all the [JSONProperty]s of this object.
     */
    fun getProperties(): List<JSONProperty> = properties.toList()

    /**
     * Does the object have a [JSONProperty] with the given [key]?
     * @param key The JSON property key.
     * @return True if any of the object's properties match the given key; False, otherwise.
     */
    fun hasProperty(key: String): Boolean = properties.any { it.key == key }

    /**
     * Add a new [JSONProperty] to the object if the object has no existing property with the same [key].
     * Otherwise, throws an [IllegalStateException].
     * @param key The [JSONProperty] key.
     * @param value The [JSONProperty] value.
     * @throws IllegalStateException If the object already contains a property with the given key.
     */
    fun addProperty(key: String, value: JSONElement) {
        if (hasProperty(key)) throw IllegalStateException("JSON object already has a property with key $key!")
        val property = JSONProperty(key, value)
        properties.add(property)
        property.owner = this
        listeners.forEach { it.onPropertyAdded(property) }
    }

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
        if (hasProperty(key)) {
            val old = properties[properties.indexOfFirst { it.key == key }]
            val new = JSONProperty(key, value)
            new.owner = this
            properties[properties.indexOfFirst { it.key == key }] = new
            listeners.forEach { it.onPropertyUpdated(old, new) }
        }
        else addProperty(key, value)
    }

    /**
     * Removes the [JSONProperty] with the given [key].
     * @param key The [JSONProperty] key.
     */
    fun removeProperty(key: String) {
        if (!hasProperty(key)) return
        val indexToRemove = properties.indexOfFirst { it.key == key }
        val removed = properties[indexToRemove]
        properties.removeAt(indexToRemove)
        listeners.forEach { it.onPropertyRemoved(removed) }
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