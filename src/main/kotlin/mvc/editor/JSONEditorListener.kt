package mvc.editor

import model.elements.JSONArray
import model.elements.JSONElement
import model.elements.JSONObject
import model.elements.JSONProperty

/**
 * Listens for actions performed on [JSONEditor]s.
 */
interface JSONEditorListener {
    /**
     * Called when the Undo button is pressed on the [JSONEditor].
     */
    fun onUndo() { }

    /**
     * Called when the Redo button is pressed on the [JSONEditor].
     */
    fun onRedo() { }

    /**
     * Called when the [JSONEditor] is used to remove a [JSONProperty] from a [JSONObject].
     * @param obj The object from which the property was removed.
     * @param property The property that was removed.
     */
    fun onObjectPropertyRemoved(obj: JSONObject, property: JSONProperty) { }

    /**
     * Called when the [JSONEditor] is used to add a [JSONProperty] to a [JSONObject].
     * @param obj The object to which the property was added.
     * @param key The key of the property that was added.
     * @param value The value of the property that was added.
     */
    fun onObjectPropertyAdded(obj: JSONObject, key: String, value: JSONElement) { }

    /**
     * Called when the [JSONEditor] is used to update the value of a [JSONObject]'s [JSONProperty].
     * @param obj The object in which the property value was updated.
     * @param key The key of the property which was updated.
     * @param value The new value of the property.
     */
    fun onObjectPropertyChanged(obj: JSONObject, key: String, value: JSONElement) { }

    /**
     * Called when the [JSONEditor] is used to add a [JSONElement] to a [JSONArray].
     * @param array The array to which the element was added.
     * @param element The element that was added.
     */
    fun onArrayElementAdded(array: JSONArray, element: JSONElement) { }

    /**
     * Called when the [JSONEditor] is used to remove a [JSONElement] from a [JSONArray].
     * @param array The array from which the element was removed.
     * @param element The element that was removed.
     */
    fun onArrayElementRemoved(array: JSONArray, element: JSONElement) { }

    /**
     * Called when the [JSONEditor] is used to replace a [JSONElement] in a [JSONArray] with a new element.
     * @param array The array whose element was replaced.
     * @param old The element that was replaced.
     * @param new The new element.
     */
    fun onArrayElementReplaced(array: JSONArray, old: JSONElement, new: JSONElement) { }
}