package mvc.commands

import model.elements.JSONArray
import model.elements.JSONElement

/**
 * [Command] that sets a [JSONElement] to a given index of a [JSONArray] when executed.
 * @param array The array in which the element is set.
 * @param index The index to set the element to.
 * @param element The element which is set at [index].
 */
class SetArrayElement(private val array: JSONArray, private val index: Int, private val element: JSONElement): Command {
    private val backup: JSONElement = array.get(index)

    override fun execute() = array.set(index, element)
    override fun undo() = array.set(index, backup)
}