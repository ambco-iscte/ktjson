package dynamic

import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure

internal val KClass<*>.dataClassFields: List<KProperty<*>>
    get() {
        require(isData) { "Class ${this.simpleName} is not data class" }
        return primaryConstructor!!.parameters.map { p -> declaredMemberProperties.find { it.name == p.name }!! }
    }

val KClassifier?.isEnum: Boolean
    get() = this is KClass<*> && this.isSubclassOf(Enum::class)

internal val <T : Any> KClass<T>.enumConstants: List<T> get() {
    require(isEnum) { "instance must be enum" }
    return java.enumConstants.toList()
}

internal val KProperty<*>.isSerializable: Boolean
    get() = !hasAnnotation<DoNotSerialize>()

internal val KProperty<*>.annotatedName: String
    get() = if (hasAnnotation<SerializeAs>()) findAnnotation<SerializeAs>()!!.value else name

internal fun KProperty<*>.annotatedValue(instance: Any?): Any? =
    if (hasAnnotation<Stringify>()) call(instance).toString()
    else call(instance)

internal fun listOf(elements: Collection<*>): List<*> = listOf(*elements.toTypedArray())

internal fun setOf(elements: Collection<*>): Set<*> = setOf(*elements.toTypedArray())

internal fun mapOf(elements: Collection<Pair<*, *>>): Map<*, *> = mapOf(*elements.toTypedArray())

internal fun constructor(type: KClass<*>): KFunction<*>? {
    if (type.isSubclassOf(List::class))
        return ::listOf
    if (type.isSubclassOf(Set::class))
        return ::setOf
    if (type.isSubclassOf(Map::class))
        return ::mapOf
    return type.primaryConstructor
}

internal val KType.fullName: String
    get() = this.jvmErasure.simpleName + (if (arguments.isNotEmpty()) "<${
        arguments.map { it.type?.fullName }.joinToString(", ")
    }>" else "")

internal fun KClass<*>.getFunction(name: String, vararg parameterTypes: KClass<*>): KFunction<*>? =
    functions.find { it.name == name && it.parameters.map { param -> param.type.jvmErasure } == parameterTypes.toList() }

fun main() {
    data class Student(val name: String, val subjects: List<String>)
    val student = Student("Person", listOf("PA", "ELP"))
    println(student::class.primaryConstructor?.parameters?.map { it.type.fullName })
}