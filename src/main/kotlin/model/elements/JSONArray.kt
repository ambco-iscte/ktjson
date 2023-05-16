package model.elements

import model.JSONVisitor
import model.listeners.JSONArrayListener

/**
 * JSON array. Can contain any [JSONElement]s.
 * @property elements A [MutableList] of the [JSONElement]s contained in the array.
 */
data class JSONArray(private val elements: MutableList<JSONElement>): JSONElement() {
    private val listeners: MutableList<JSONArrayListener> = mutableListOf()

    init {
        elements.forEach { it.owner = this }
    }

    companion object {
        /**
         * Empty JSON array.
         */
        fun empty(): JSONArray = JSONArray(mutableListOf())
    }

    /**
     * Attaches a [JSONArrayListener] to this [JSONArray].
     */
    fun addListener(listener: JSONArrayListener) = listeners.add(listener)

    /**
     * Removes a [JSONArrayListener] from this [JSONArray].
     */
    fun removeListener(listener: JSONArrayListener) = listeners.remove(listener)

    /**
     * Returns a [List] of all the [JSONElement]s contained in the array.
     */
    fun getElements(): List<JSONElement> = elements.toList()

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
        element.owner = this
        listeners.forEach { it.onElementAdded(element) }
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
        if (index < 0) return
        elements[index] = element
        element.owner = this
        listeners.forEach { it.onElementSet(index, element) }
    }

    /**
     * Replaces a [JSONElement] with a new element.
     * @param old The element to replace.
     * @param new The new element to add.
     */
    fun replace(old: JSONElement, new: JSONElement) = set(elements.indexOf(old), new)

    /**
     * Removes and returns the [JSONElement] from the position [index].
     * @param index The index to remove from.
     */
    fun removeAt(index: Int): JSONElement {
        listeners.forEach { it.onElementRemoved(index, elements[index]) }
        return elements.removeAt(index)
    }

    /**
     * Removes a [JSONElement] from the array.
     * @param element The element to remove.
     */
    fun remove(element: JSONElement) {
        val index = elements.indexOf(element)
        if (elements.remove(element)) listeners.forEach { it.onElementRemoved(index, element) }
    }

    override fun toString(): String = "[\n${elements.joinToString(",\n")}\n${"\t".repeat(depth)}]"

    override fun accept(visitor: JSONVisitor) {
        if (visitor.visit(this))
            elements.forEach { it.accept(visitor) }
        visitor.endVisit(this)
    }
}