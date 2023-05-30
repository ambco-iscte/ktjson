package mvc.components.tooltips

import model.elements.JSONArray
import model.elements.JSONElement
import mvc.editor.JSONEditor
import javax.swing.JMenuItem
import javax.swing.JPopupMenu

// Generic JSONEditor tooltip.
internal open class JSONEditorTooltip(open val element: JSONElement, open val editor: JSONEditor): JPopupMenu() {
    fun withArrayRemoval(array: JSONArray): JSONEditorTooltip {
        add(JMenuItem("Remove from Array").apply { addActionListener {
            editor.onArrayElementRemoved(array, element) }
        })
        return this
    }
}