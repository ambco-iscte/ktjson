package model

import model.elements.JSONElement
import model.elements.JSONProperty

// Pretty indentation :)
internal val preamble: (JSONElement) -> String = { d -> if (d.owner is JSONProperty) "" else "\t".repeat(d.depth) }