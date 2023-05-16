package mvc.commands

import model.elements.JSONArray
import model.elements.JSONElement

/**
 * [Command] that removes a [JSONElement] from a [JSONArray] when executed.
 * @param array The array from which the element is removed.
 * @param element The element to remove.
 */
class RemoveArrayElement(private val array: JSONArray, private val element: JSONElement): Command {
    override fun execute() = array.remove(element)
    override fun undo() = array.add(element)
}