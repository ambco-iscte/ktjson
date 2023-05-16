package model.listeners

import model.elements.JSONElement

/**
 * Listens for actions performed on [JSONArray]s.
 */
interface JSONArrayListener {
    /**
     * Called when a [JSONElement] is added to a [JSONArray].
     * @param element The element that was added.
     */
    fun onElementAdded(element: JSONElement) { }

    /**
     * Called when a [JSONElement] is set to an [index] of a [JSONArray].
     * @param index The index where the element was set.
     * @param element The element that was set.
     */
    fun onElementSet(index: Int, element: JSONElement) { }

    /**
     * Called when a [JSONElement] is removed from a [JSONArray].
     * @param index The index from where the element was removed.
     * @param element The element that was removed.
     */
    fun onElementRemoved(index: Int, element: JSONElement) { }
}