package parsing

import antlr.JSONLexer
import antlr.JSONParser
import model.JSONElement
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

/**
 * Reads JSON content from a file with name [fileName] and parses it to an abstract [JSONElement].
 * @param fileName The name of the file to read.
 * @return The [JSONElement] constructed from the file's content.
 */
fun parse(fileName: String): JSONElement = JSONParser(
    CommonTokenStream(JSONLexer(CharStreams.fromFileName(fileName)))
).json().toAbstractTree()