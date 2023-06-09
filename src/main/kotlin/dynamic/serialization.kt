package dynamic

import model.elements.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.cast
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

/**
 * Represents an exception that occurred when deserializing a [JSONElement].
 * @property message The exception message.
 */
data class DeserializationException(override val message: String?) : Exception(message)

/**
 * Serializes this object to an abstract [JSONElement].
 * @return The instance or value, serialized as a JSON element.
 * @see [serializeJSON]
 */
fun Any?.serialize(): JSONElement = serializeJSON(this)

/**
 * Serializes an [instance] of any data class, primitive type, or collection type, to an abstract [JSONElement].
 */
private fun serializeJSON(instance: Any?): JSONElement {
    when (instance) {
        null -> return Null()
        is String -> return JSONString(instance)
        is Number -> return JSONNumber(instance)
        is Boolean -> return JSONBoolean(instance)
        is Collection<*> -> return JSONArray(instance.map { serializeJSON(it) }.toMutableList())
        is Enum<*> -> return serializeJSON(instance.name)
        is Map<*, *> -> return JSONObject(instance.entries.map {
                entry -> JSONProperty(entry.key.toString(), serializeJSON(entry.value))
        }.toMutableList())
    }
    if (instance!!::class.isData) {
        val fields = instance::class.dataClassFields
        return JSONObject(fields.filter { it.isSerializable }.map {
            JSONProperty(it.annotatedName, serializeJSON(it.annotatedValue(instance)))
        }.toMutableList())
    }
    return JSONObject.empty()
}

/**
 * Deserializes a JSON element [json] to this class. Returns an object of type [Any] corresponding to the deserialized object.
 * @param json The [JSONElement] to deserialize.
 * @see [deserializeJSON]
 */
fun KClass<*>.deserialize(json: JSONElement): Any? = deserializeJSON(json, createType())

/**
 * Deserializes a JSON element [json] to the provided [type].
 * @param json The [JSONElement] to deserialize.
 * @param type The type to deserialize the element to.
 * @return A new instance corresponding to the deserialized JSON element.
 */
private fun deserializeJSON(json: JSONElement, type: KType): Any? {
    val clazz = type.jvmErasure // KClass corresponding to the KType
    when (json) {
        is Null -> return null
        is JSONString -> {
            if (clazz.isEnum && clazz.enumConstants.isNotEmpty())
                return clazz.cast(clazz.enumValueOf(json.string))
            return clazz.cast(json.string)
        }
        is JSONNumber -> return clazz.cast(json.number)
        is JSONBoolean -> return clazz.cast(json.boolean)
        else -> {
            val constructor = constructor(clazz) ?: throw DeserializationException("Type ${type.fullName} has no constructor")
            val params = constructor.parameters.associateBy { it.name } // <ParameterName, Parameter>
            val args = mutableMapOf<KParameter, Any?>() // <Parameter, Argument>
            when (json) {
                is JSONArray -> { // Should only deserialize to Collection (List, Set, Array, ...) types
                    if (clazz.isSubclassOf(Collection::class)) {
                        if (type.arguments.isEmpty() || type.arguments[0].type == null)
                            throw DeserializationException("Collection type ${type.fullName} has non-existent or null type parameters")
                        args[params[params.keys.first()]!!] = json.getElements().map {
                            // Deserialize each element to the element type of the Collection
                            deserializeJSON(it, type.arguments[0].type!!) // Collection<type.arguments[0].type>
                        }
                    }
                    else throw DeserializationException("Can't deserialize JSON array to non-collection type ${type.fullName}")
                }
                is JSONObject -> { // Either deserializes to a Map, or to another Object
                    if (clazz.isSubclassOf(Map::class)) {
                        if (type.arguments.size < 2 || type.arguments[1].type == null)
                            throw DeserializationException("Map type ${type.fullName} has non-existent or null value type parameter")
                        args[params[params.keys.first()]!!] = json.getProperties().map {
                            // Map each key to its corresponding value deserialized to the Map's value type
                            it.key to deserializeJSON(it.value, type.arguments[1].type!!) // Map<K, type.arguments[1].type>
                        }
                    }
                    else json.getProperties().forEach { // Deserialize each property to its corresponding parameter's type
                        if (!params.containsKey(it.key))
                            throw DeserializationException("Primary constructor of type ${type.fullName} has no parameter ${it.key}")
                        val param = params[it.key]!!
                        args[param] = deserializeJSON(it.value, param.type)
                    }
                }
                is JSONProperty -> { // Deserialize the property to its corresponding parameter's type
                    if (!params.containsKey(json.key))
                        throw DeserializationException("Primary constructor of type ${type.fullName} has no parameter ${json.key}")
                    val param = params[json.key]!!
                    args[param] = deserializeJSON(json.value, param.type)
                }
                else -> throw DeserializationException("Unsupported JSON element: ${json::class.simpleName}")
            }
            return constructor(clazz)!!.callBy(args) // Create a new instance with the given arguments
        }
    }
}