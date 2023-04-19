package dynamic

import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure

/**
 * Obtains a list of [KProperty]s ordered in the same order as they appear in the class's primary constructor.
 * @author André L. Santos
 * @see <p>Advanced Programming course materials on Reflection</p>
 */
internal val KClass<*>.dataClassFields: List<KProperty<*>>
    get() {
        require(isData) { "Class ${this.simpleName} is not data class" }
        return primaryConstructor!!.parameters.map { p -> declaredMemberProperties.find { it.name == p.name }!! }
    }

/**
 * Is the classifier an [Enum]?
 * @author André L. Santos
 * @see <p>Advanced Programming course materials on Reflection</p>
 */
internal val KClassifier?.isEnum: Boolean
    get() = this is KClass<*> && this.isSubclassOf(Enum::class)

/**
 * Obtains a list of an [Enum] class's constants.
 * @author André L. Santos
 * @see <p>Advanced Programming course materials on Reflection</p>
 */
internal val <T : Any> KClass<T>.enumConstants: List<T> get() {
    require(isEnum) { "instance must be enum" }
    return java.enumConstants.toList()
}

/**
 * Is this property JSON-serializable?
 * @see [DoNotSerialize]
 */
internal val KProperty<*>.isSerializable: Boolean
    get() = !hasAnnotation<DoNotSerialize>()

/**
 * The target JSON-serialized name of this property.
 * @see [SerializeAs]
 */
internal val KProperty<*>.annotatedName: String
    get() = if (hasAnnotation<SerializeAs>()) findAnnotation<SerializeAs>()!!.value else name

/**
 * Obtains the target JSON-serialized value of this [KProperty].
 * @param instance The object where the property is stored.
 * @return The property's value's string representation, if the [Stringify] annotation is present;
 * The plain value, otherwise.
 * @see [Stringify]
 */
internal fun KProperty<*>.annotatedValue(instance: Any?): Any? =
    if (hasAnnotation<Stringify>()) call(instance).toString()
    else call(instance)

/**
 * Creates a [List] from a [Collection] of elements.
 * @param elements A [Collection] of elements.
 * @return The [Collection] of elements converted to a Kotlin immutable [List].
 */
internal fun listOf(elements: Collection<*>): List<*> = listOf(*elements.toTypedArray())

/**
 * Creates a [Set] from a [Collection] of elements.
 * @param elements A collection of elements.
 * @return The [Collection] of elements converted to a Kotlin immutable [Set].
 */
internal fun setOf(elements: Collection<*>): Set<*> = setOf(*elements.toTypedArray())

/**
 * Creates a [List] from a [Collection] of [Pair]s.
 * @param elements A [Collection] of [Pair]s.
 * @return The [Collection] of [Pair]s converted to a Kotlin immutable [Map].
 */
internal fun mapOf(elements: Collection<Pair<*, *>>): Map<*, *> = mapOf(*elements.toTypedArray())

/**
 * Obtains the primary constructor for a given [KClass]. Supports [Collection] types, which have no constructor.
 * @param type The [KClass] to obtain the constructor for.
 * @return The function acting as the [KClass]'s constructor.
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
 * The full name of this [KType], including any [KTypeProjection] parameters.
 */
internal val KType.fullName: String
    get() = this.jvmErasure.simpleName + (if (arguments.isNotEmpty()) "<${
        arguments.map { it.type?.fullName }.joinToString(", ")
    }>" else "")

/**
 * Finds a [KFunction] declared by this [KClass] that matches the given name and parameter types.
 * @param name The name of the [KFunction].
 * @param parameterTypes The parameter types of the [KFunction].
 * @return The first matching [KFunction], if found; Null, otherwise.
 */
internal fun KClass<*>.getFunction(name: String, vararg parameterTypes: KClass<*>): KFunction<*>? =
    functions.find { it.name == name && it.parameters.map { param -> param.type.jvmErasure } == parameterTypes.toList() }

/**
 * Obtains an enum constant of this [KClass] from its [String] representation. The current [KClass] must be an [Enum] class.
 * @param string The [String] representation of the [Enum] constant.
 * @return The [Enum] constant corresponding to the given [String].
 * @throws IllegalArgumentException If the current [KClass] is not an [Enum] class or no [Enum] constants are defined.
 */
internal fun KClass<*>.enumValueOf(string: String): Enum<*> {
    require(isSubclassOf(Enum::class) && enumConstants.isNotEmpty()) {
        "Cannot invoke valueOf: ${this.simpleName} is not enum class or no constants are defined"
    }
    return getFunction("valueOf", String::class)!!.call(string) as Enum<*>
}