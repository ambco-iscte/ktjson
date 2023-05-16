package mvc.commands

import model.elements.JSONArray
import model.elements.JSONElement

/**
 * [Command] that replaces a [JSONElement] in a [JSONArray] with a new element when executed.
 * @param array The array whose element is replaced.
 * @param old The element to replace.
 * @param new The new element.
 */
class ReplaceArrayElement(private val array: JSONArray, private val old: JSONElement, private val new: JSONElement) : Command {
    override fun execute() = array.replace(old, new)
    override fun undo() = array.replace(new, old)
}