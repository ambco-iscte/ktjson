package model.elements

import model.JSONVisitor
import model.preamble

/**
 * Null [JSONElement].
 */
class Null: JSONElement() {
    override fun toString(): String = preamble(this) + "null"

    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }

    override fun equals(other: Any?): Boolean = if (other == null) false else other is Null

    override fun hashCode(): Int = 0 // TODO?
}