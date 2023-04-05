package parsing

import JSONLexer
import JSONParser
import model.JSONElement
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

/**
 * Reads JSON content from a file and parses it to an abstract JSON model.
 * @param fileName The name of the file to read.
 * @return The JSON model constructed from the file's content.
 */
fun parse(fileName: String): JSONElement = JSONParser(
    CommonTokenStream(JSONLexer(CharStreams.fromFileName(fileName)))
).json().toAbstractTree()