package mvc.commands

import model.elements.JSONElement
import model.elements.JSONObject
import model.elements.JSONProperty

/**
 * [Command] that updates the value of a [JSONProperty] of a [JSONObject] when executed.
 * @param obj The object whose property is updated.
 * @param key The key of the property to update.
 * @param value The new value of the property.
 */
class UpdateObjectProperty(private val obj: JSONObject, private val key: String, private val value: JSONElement): Command {
    private val backup: JSONElement? = if (obj.hasProperty(key)) obj.getProperty(key) else null

    override fun execute() = obj.setProperty(key, value)
    override fun undo() = if (backup == null) obj.removeProperty(key) else obj.setProperty(key, backup)
}