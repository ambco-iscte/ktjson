package model.elements

import model.JSONVisitor
import model.preamble

/**
 * JSON boolean.
 * @property boolean The boolean value held by this [JSONElement].
 */
data class JSONBoolean(val boolean: Boolean): JSONElement() {
    override fun toString(): String = preamble(this) + boolean.toString()
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}