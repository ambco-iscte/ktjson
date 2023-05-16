package mvc.commands

import model.elements.JSONArray
import model.elements.JSONElement

/**
 * [Command] that adds a [JSONElement] to a [JSONArray] when executed.
 * @param array The array to which the element is added.
 * @param element The element to add.
 */
class AddArrayElement(private val array: JSONArray, private val element: JSONElement): Command {
    override fun execute() = array.add(element)
    override fun undo() = array.remove(element)
}