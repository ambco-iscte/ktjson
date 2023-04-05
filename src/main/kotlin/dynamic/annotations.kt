package dynamic

/**
 * Class properties annotated with DoNotSerialize are not serialized to JSON.
 * @see serialize
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class DoNotSerialize()

/**
 * Class properties annotated with SerializeAs(someName) ignore their name and are serialized as "someName" instead.
 * @see serialize
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class SerializeAs(val value: String)

/**
 * Class properties annotated with Stringify are serialized as Strings regardless of their type.
 * @see serialize
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Stringify()