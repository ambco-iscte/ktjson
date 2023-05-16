package mvc.commands

import model.elements.JSONObject
import model.elements.JSONProperty

/**
 * [Command] that adds a [JSONProperty] to a [JSONObject] when executed.
 * @param obj The object to which the property is added.
 * @param property The property to add.
 */
class AddObjectProperty(private val obj: JSONObject, private val property: JSONProperty): Command {
    override fun execute() = obj.addProperty(property.key, property.value)
    override fun undo() = obj.removeProperty(property.key)
}