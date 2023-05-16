package model.elements

import model.JSONVisitor
import model.preamble

/**
 * JSON string.
 * @property string The string held by this [JSONElement].
 */
data class JSONString(val string: String): JSONElement() {
    override fun toString(): String = preamble(this) + "\"$string\""
    override fun accept(visitor: JSONVisitor) {
        visitor.visit(this)
    }
}