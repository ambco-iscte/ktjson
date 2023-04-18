package parsing

import antlr.JSONParser
import model.*

/**
 * JSON parsing exception.
 * @property message The exception message.
 * @property context The [JSONParser.ElementContext] that generated the exception.
 */
data class JSONParseException(override val message: String, val context: JSONParser.ElementContext): Exception(message)

/**
 * Projects a parsed JSON document to its abstract [JSONElement].
 * @return The [JSONObject] corresponding to this parsed JSON document context.
 */
fun JSONParser.JsonContext.toAbstractTree(): JSONObject = `object`().toAbstractTree()

/**
 * Projects a parsed JSON Object to its abstract [JSONObject].
 * @return The [JSONObject] corresponding to this parsed JSON object context.
 */
fun JSONParser.ObjectContext.toAbstractTree(): JSONObject = JSONObject(property().map { it.toAbstractTree() }.toMutableList())

/**
 * Projects a parsed JSON Property to its abstract [JSONProperty].
 * @return The [JSONProperty] corresponding to this parsed JSON property context.
 */
fun JSONParser.PropertyContext.toAbstractTree(): JSONProperty = JSONProperty(STRING().text.removeSurrounding("\""), element().toAbstractTree())

/**
 * Projects a parsed JSON Array to its abstract [JSONArray].
 * @return The [JSONArray] corresponding to this parsed JSON array context.
 */
fun JSONParser.ArrayContext.toAbstractTree(): JSONArray = JSONArray(element().map { it.toAbstractTree() }.toMutableList())

/**
 * Projects a parsed JSON Element to its abstract [JSONElement].
 * @return The [JSONElement] corresponding to this parsed JSON element context.
 */
fun JSONParser.ElementContext.toAbstractTree(): JSONElement =
    if (STRING() != null) JSONString(STRING().text.removeSurrounding("\""))
    else if (NUMBER() != null) JSONNumber(NUMBER().text.toIntOrNull() ?: NUMBER().text.toDouble())
    else if (BOOLEAN() != null) JSONBoolean(BOOLEAN().text.toBoolean())
    else if (NULL() != null) Null
    else if (array() != null) array().toAbstractTree()
    else if (`object`() != null) `object`().toAbstractTree()
    else throw JSONParseException("Invalid abstract JSON element", this)