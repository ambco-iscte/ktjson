package model.elements

import model.JSONVisitor
import model.preamble

/**
 * JSON number.
 * @property number The number value held by this [JSONElement].
 */
data class JSONNumber(val number: Number): JSONElement() {
    override fun toString(): String = preamble(this) + number.toString()
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}