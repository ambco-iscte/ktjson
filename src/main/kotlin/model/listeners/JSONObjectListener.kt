package model.listeners

import model.elements.JSONProperty

/**
 * Listens for actions performed on [JSONObject]s.
 */
interface JSONObjectListener {
    /**
     * Called when a [JSONProperty] is added to a [JSONObject].
     * @param property The property that was added.
     */
    fun onPropertyAdded(property: JSONProperty) { }

    /**
     * Called when a [JSONProperty] is removed from a [JSONObject].
     * @param property The property that was removed.
     */
    fun onPropertyRemoved(property: JSONProperty) { }

    /**
     * Called when a [JSONProperty] is updated on a [JSONObject].
     * @param old The old property.
     * @param new The new property.
     */
    fun onPropertyUpdated(old: JSONProperty, new: JSONProperty) { }
}