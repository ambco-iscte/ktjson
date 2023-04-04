package dynamic

import model.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.cast
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.jvmErasure

data class DeserializationException(override val message: String?) : Exception(message)

fun serialize(instance: Any?): JSONElement {
    when (instance) {
        null -> return Null
        is String -> return JSONString(instance)
        is Number -> return JSONNumber(instance)
        is Boolean -> return JSONBoolean(instance)
        is Collection<*> -> return JSONArray(instance.map { serialize(it) })
        is Enum<*> -> return serialize(instance.name)
        is Map<*, *> -> return JSONObject(instance.entries.map {
                entry -> JSONProperty(entry.key.toString(), serialize(entry.value))
        })
    }
    if (instance!!::class.isData) {
        val fields = instance::class.dataClassFields
        return JSONObject(fields.filter { it.isSerializable }.map {
            JSONProperty(it.annotatedName, serialize(it.annotatedValue(instance)))
        })
    }
    return JSONObject.EMPTY
}

fun KClass<*>.deserialize(json: JSONElement): Any? = deserializeJSON(json, createType())

/**
 * Deserializes a JSON element to the appropriate type.
 * @param json The JSON element to deserialize.
 * @param type The type to deserialize the element to.
 * @return A new instance corresponding to the deserialized JSON element.
 */
private fun deserializeJSON(json: JSONElement, type: KType): Any? {
    val clazz = type.jvmErasure // KClass corresponding to the KType
    when (json) {
        is Null -> return null
        is JSONString -> {
            if (clazz.isSubclassOf(Enum::class) && clazz.enumConstants.firstOrNull() != null) {
                val valueOf = clazz.getFunction("valueOf", String::class)
                return valueOf!!.call(json.string) as Enum<*>
            }
            return clazz.cast(json.string)
        }
        is JSONNumber -> return clazz.cast(json.number)
        is JSONBoolean -> return clazz.cast(json.boolean)
        else -> {
            val params = constructor(clazz)!!.parameters.associateBy { it.name } // <ParameterName, Parameter>
            val args = mutableMapOf<KParameter, Any?>() // <Parameter, Argument>
            when (json) {
                is JSONArray -> { // Should only deserialize to Collection (List, Set, Array, ...) types
                    if (clazz.isSubclassOf(Collection::class)) {
                        args[params[params.keys.first()]!!] = json.elements.map {
                            // Deserialize each element to the element type of the Collection
                            deserializeJSON(it, type.arguments[0].type!!) // Collection<type.arguments[0].type>
                        }
                    }
                    else throw DeserializationException("Can't deserialize JSON array to non-collection type ${type.fullName}")
                }
                is JSONObject -> { // Either deserializes to a Map, or to another Object
                    if (clazz.isSubclassOf(Map::class)) args[params[params.keys.first()]!!] = json.properties.map {
                        // Map each key to its corresponding value deserialized to the Map's value type
                        it.key to deserializeJSON(it.value, type.arguments[1].type!!) // Map<K, type.arguments[1].type>
                    }
                    else json.properties.forEach { // Deserialize each property to its corresponding parameter's type
                        val param = params[it.key]!!
                        args[param] = deserializeJSON(it.value, param.type)
                    }
                }
                is JSONProperty -> { // Deserialize the property to its corresponding parameter's type
                    val param = params[json.key]!!
                    args[param] = deserializeJSON(json.value, param.type)
                }
                else -> throw DeserializationException("Unsupported JSON element: ${json::class.simpleName}")
            }
            return constructor(clazz)!!.callBy(args) // Create a new instance with the given arguments
        }
    }
}

// Testing
fun main() {
    data class Student(val name: String, val number: Int)
    val student = Student("Afonso", 92494)
    println(Student::class.deserialize(serialize(student)))
}