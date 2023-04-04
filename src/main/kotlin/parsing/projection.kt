package parsing

import JSONParser
import model.*

fun JSONParser.JsonContext.toAbstractTree(): JSONObject = `object`().toAbstractTree()

fun JSONParser.ObjectContext.toAbstractTree(): JSONObject = JSONObject(property().map { it.toAbstractTree() })

fun JSONParser.PropertyContext.toAbstractTree(): JSONProperty = JSONProperty(STRING().text.removeSurrounding("\""), element().toAbstractTree())

fun JSONParser.ArrayContext.toAbstractTree(): JSONArray = JSONArray(element().map { it.toAbstractTree() })

fun JSONParser.ElementContext.toAbstractTree(): JSONElement =
    if (STRING() != null) JSONString(STRING().text.removeSurrounding("\""))
    else if (NUMBER() != null) JSONNumber(NUMBER().text.toIntOrNull() ?: NUMBER().text.toDouble())
    else if (BOOLEAN() != null) JSONBoolean(BOOLEAN().text.toBoolean())
    else if (NULL() != null) Null
    else if (array() != null) array().toAbstractTree()
    else if (`object`() != null) `object`().toAbstractTree()
    else throw Exception("Invalid abstract JSON element: $text")