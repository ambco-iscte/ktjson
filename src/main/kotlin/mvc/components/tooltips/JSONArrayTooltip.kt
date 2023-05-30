package mvc.components.tooltips

import model.elements.JSONArray
import model.elements.JSONObject
import model.elements.Null
import mvc.editor.JSONEditor
import javax.swing.JMenuItem

// Tooltip shown when a JSONArrayPanel is right-clicked.
internal data class JSONArrayTooltip(val array: JSONArray, override val editor: JSONEditor): JSONEditorTooltip(array, editor) {
    init {
        add(JMenuItem("Add Object Element").apply {
            addActionListener { editor.onArrayElementAdded(array, JSONObject.empty()) }
        })
        add(JMenuItem("Add Array Element").apply {
            addActionListener { editor.onArrayElementAdded(array, JSONArray.empty()) }
        })
        add(JMenuItem("Add Data Element").apply {
            addActionListener { editor.onArrayElementAdded(array, Null()) }
        })
    }
}