package mvc.commands

import model.elements.JSONElement
import model.elements.JSONObject
import model.elements.JSONProperty

/**
 * [Command] that removes a [JSONProperty] from a [JSONObject] when executed.
 * @param obj The object from which the property is removed.
 * @param key The key of the property to remove.
 */
class RemoveObjectProperty(private val obj: JSONObject, private val key: String): Command {
    private val backup: JSONElement = obj.getProperty(key)

    override fun execute() = obj.removeProperty(key)
    override fun undo() = obj.addProperty(key, backup)
}