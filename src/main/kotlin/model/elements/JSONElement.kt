package model.elements

import JSONLexer
import JSONParser
import model.IAcceptVisitors
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import parsing.toAbstractTree
import kotlin.reflect.KClass

/**
 * Generic JSON element.
 * @property owner The "owner" of this element.
 * Objects own their properties, arrays own their elements, and properties own their values.
 */
sealed class JSONElement(internal var owner: JSONElement? = null): IAcceptVisitors {
    internal val depth: Int
        get() = 1 + (owner?.depth ?: -1) + (if (owner is JSONProperty) -1 else 0)

    /**
     * Finds the first ancestor of the current [JSONElement] whose class matches [type] and which verifies a [predicate].
     * @param type Any class that extends from [JSONElement].
     * @param predicate A predicate which takes a [JSONElement] and returns a [Boolean].
     */
    fun findAncestor(type: KClass<out JSONElement>, predicate: (JSONElement) -> Boolean = {true}): JSONElement? {
        tailrec fun findAncestor(current: JSONElement?): JSONElement? =
            if (current == null) null
            else if (current::class == type && predicate(current)) current
            else findAncestor(current.owner)
        return findAncestor(owner)
    }

    companion object {
        /**
         * Parses a [String] to a literal [JSONElement], i.e. [Null], [JSONBoolean], [JSONNumber], or [JSONString].
         * @param string The string to parse.
         */
        fun parseLiteral(string: String): JSONElement =
            if (string == "null") Null()
            else if (string == "true" || string == "false") JSONBoolean(string.toBoolean())
            else if (string.toDoubleOrNull() != null) JSONNumber(string.toIntOrNull() ?: string.toDouble())
            else JSONString(string)

        /**
         * Reads JSON content from a file with name [fileName] and parses it to an abstract [JSONElement].
         * @param fileName The name of the file to read.
         * @return The [JSONElement] constructed from the file's content.
         */
        fun parse(fileName: String): JSONElement = JSONParser(
            CommonTokenStream(JSONLexer(CharStreams.fromFileName(fileName)))
        ).document().toAbstractTree()
    }
}