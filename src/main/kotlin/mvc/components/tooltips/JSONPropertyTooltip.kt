package mvc.components.tooltips

import model.elements.JSONObject
import model.elements.JSONProperty
import mvc.editor.JSONEditor
import javax.swing.JMenuItem

// Tooltip shown when a JSONPropertyPanel is right-clicked.
internal data class JSONPropertyTooltip(val obj: JSONObject, val property: JSONProperty, override val editor: JSONEditor): JSONEditorTooltip(property, editor) {
    init {
        add(JMenuItem("Remove").apply {
            addActionListener {
                editor.onPropertyRemoved(obj, property)
            }
        })
    }
}