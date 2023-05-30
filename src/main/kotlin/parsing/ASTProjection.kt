package parsing

import JSONParser
import model.elements.*
import org.antlr.v4.runtime.ParserRuleContext

/**
 * JSON parsing exception.
 * @property message The exception message.
 * @property context The ANTLR [ParserRuleContext] that generated the exception.
 */
data class JSONParseException(override val message: String, val context: ParserRuleContext): Exception(message)

internal fun JSONParser.DocumentContext.toAbstractTree(): JSONObject = composite().toAbstractTree()

internal fun JSONParser.CompositeContext.toAbstractTree(): JSONObject = JSONObject(property().map { it.toAbstractTree() }.toMutableList())

internal fun JSONParser.PropertyContext.toAbstractTree(): JSONProperty = JSONProperty(STRING().text.removeSurrounding("\""), element().toAbstractTree())

internal fun JSONParser.CollectionContext.toAbstractTree(): JSONArray = JSONArray(element().map { it.toAbstractTree() }.toMutableList())

internal fun JSONParser.ElementContext.toAbstractTree(): JSONElement = when (this) {
    is JSONParser.LiteralContext -> value.toAbstractTree()
    is JSONParser.ArrayContext -> array.toAbstractTree()
    is JSONParser.ObjectContext -> `object`.toAbstractTree()
    else -> throw JSONParseException("Unrecognised JSON element context", this)
}

internal fun JSONParser.PrimitiveContext.toAbstractTree(): JSONElement = when (this) {
    is JSONParser.StringContext -> JSONString(string.text.removeSurrounding("\""))
    is JSONParser.NumberContext -> JSONNumber(number.text.toIntOrNull() ?: number.text.toDouble())
    is JSONParser.BooleanContext -> JSONBoolean(boolean_.text.toBoolean())
    is JSONParser.NullContext -> Null()
    else -> throw JSONParseException("Unrecognised JSON element context", this)
}
