package dynamic

import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure

/**
 * Obtains a list of class properties ordered in the same order as they appear in the class's primary constructor.
 * @author André L. Santos
 * @see <p>Advanced Programming course materials on Reflection</p>
 */
internal val KClass<*>.dataClassFields: List<KProperty<*>>
    get() {
        require(isData) { "Class ${this.simpleName} is not data class" }
        return primaryConstructor!!.parameters.map { p -> declaredMemberProperties.find { it.name == p.name }!! }
    }

/**
 * Is the classifier an enum?
 * @author André L. Santos
 * @see <p>Advanced Programming course materials on Reflection</p>
 */
val KClassifier?.isEnum: Boolean
    get() = this is KClass<*> && this.isSubclassOf(Enum::class)

/**
 * Obtains a list of an enum class's constants.
 * @author André L. Santos
 * @see <p>Advanced Programming course materials on Reflection</p>
 */
internal val <T : Any> KClass<T>.enumConstants: List<T> get() {
    require(isEnum) { "instance must be enum" }
    return java.enumConstants.toList()
}

/**
 * Is this property JSON-serializable?
 */
internal val KProperty<*>.isSerializable: Boolean
    get() = !hasAnnotation<DoNotSerialize>()

/**
 * The target JSON-serialized name of this property.
 */
internal val KProperty<*>.annotatedName: String
    get() = if (hasAnnotation<SerializeAs>()) findAnnotation<SerializeAs>()!!.value else name

/**
 * Obtains the target JSON-serialized value of this property.
 * @param instance The object where the property is stored.
 * @return The property's value's string representation, if the [Stringify] annotation is present;
 * The plain value, otherwise.
 */
internal fun KProperty<*>.annotatedValue(instance: Any?): Any? =
    if (hasAnnotation<Stringify>()) call(instance).toString()
    else call(instance)

/**
 * Creates a list from a collection of elements.
 * @param elements A collection of elements.
 * @return The collection of elements converted to a Kotlin read-only list.
 */
internal fun listOf(elements: Collection<*>): List<*> = listOf(*elements.toTypedArray())

/**
 * Creates a set from a collection of elements.
 * @param elements A collection of elements.
 * @return The collection of elements converted to a Kotlin read-only set.
 */
internal fun setOf(elements: Collection<*>): Set<*> = setOf(*elements.toTypedArray())

/**
 * Creates a list from a collection of pairs.
 * @param elements A collection of pairs.
 * @return The collection of pairs converted to a Kotlin read-only map.
 */
internal fun mapOf(elements: Collection<Pair<*, *>>): Map<*, *> = mapOf(*elements.toTypedArray())

/**
 * Obtains the primary constructor for a given type. Supports collection types, which have no constructor.
 * @param type The type to obtain the constructor for.
 * @return The function acting as the type's constructor.
 * @see listOf
 * @see setOf
 * @see mapOf
 */
internal fun constructor(type: KClass<*>): KFunction<*>? {
    if (type.isSubclassOf(List::class))
        return ::listOf
    if (type.isSubclassOf(Set::class))
        return ::setOf
    if (type.isSubclassOf(Map::class))
        return ::mapOf
    return type.primaryConstructor
}

/**
 * The full name of this type, including any type parameters.
 */
internal val KType.fullName: String
    get() = this.jvmErasure.simpleName + (if (arguments.isNotEmpty()) "<${
        arguments.map { it.type?.fullName }.joinToString(", ")
    }>" else "")

/**
 * Finds a function declared by this class that matches the given name and parameter types.
 * @param name The name of the function.
 * @param parameterTypes The parameter types of the function.
 * @return The first matching function, if found; Null, otherwise.
 */
internal fun KClass<*>.getFunction(name: String, vararg parameterTypes: KClass<*>): KFunction<*>? =
    functions.find { it.name == name && it.parameters.map { param -> param.type.jvmErasure } == parameterTypes.toList() }

/**
 * Obtains an enum constant of this class from its string representation. The current class must be an enum class.
 * @param string The string representation of the enum constant.
 * @return The enum constant corresponding to the given string.
 * @throws IllegalArgumentException If the current class is not an enum class or no enum constants are defined.
 */
internal fun KClass<*>.enumValueOf(string: String): Enum<*> {
    require(isSubclassOf(Enum::class) && enumConstants.isNotEmpty()) {
        "Cannot invoke valueOf: ${this.simpleName} is not enum class or no constants are defined"
    }
    return getFunction("valueOf", String::class)!!.call(string) as Enum<*>
}