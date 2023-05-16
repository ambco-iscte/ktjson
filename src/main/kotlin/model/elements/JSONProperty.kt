package model.elements

import model.JSONVisitor

/**
 * JSON object property.
 * @property key The name of the property.
 * @property value The value of the property. Can be any [JSONElement].
 */
data class JSONProperty(val key: String, val value: JSONElement): JSONElement() {
    init {
        value.owner = this
    }

    override fun toString(): String =  "\t".repeat(depth) + "\"$key\": $value"

    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
        value.accept(visitor)
    }
}