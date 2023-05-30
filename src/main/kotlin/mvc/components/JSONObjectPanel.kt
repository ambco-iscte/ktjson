package mvc.components

import model.elements.JSONObject
import model.elements.JSONProperty
import model.listeners.JSONObjectListener
import mvc.components.tooltips.JSONObjectTooltip
import mvc.editor.JSONEditor
import java.awt.Color
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.border.CompoundBorder

// GUI panel that displays a JSONObject.
internal data class JSONObjectPanel(val obj: JSONObject, val editor: JSONEditor): JPanel() {
    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = Color.LIGHT_GRAY
        border = CompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        )

        // Listen for changes on the model and update the GUI accordingly.
        obj.addListener(object : JSONObjectListener {
            override fun onPropertyAdded(property: JSONProperty) {
                add(JSONPropertyPanel(property, editor))
                revalidate()
                repaint()
                editor.refresh()
            }

            override fun onPropertyRemoved(property: JSONProperty) {
                remove(components.filterIsInstance<JSONPropertyPanel>().indexOfFirst { it.property == property })
                revalidate()
                repaint()
                editor.refresh()
            }

            override fun onPropertyUpdated(old: JSONProperty, new: JSONProperty) {
                val index = components.indexOfFirst {
                    it is JSONPropertyPanel && it.property == old && it.editor == editor
                }
                if (index == -1) return
                else if (new != old) {
                    remove(components[index])
                    add(JSONPropertyPanel(new, editor), index)
                }
                revalidate()
                repaint()
                editor.refresh()
            }
        })

        // Add panels to display every property already in the object.
        obj.getProperties().forEach { add(JSONPropertyPanel(it, editor)) }

        // Add a mouse listener for nice highlights and to display the tooltip when the panel is right-clicked.
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e != null && SwingUtilities.isRightMouseButton(e)) {
                    val t =
                        if (parent != null && parent is JSONArrayPanel)
                            JSONObjectTooltip(obj, editor).withArrayRemoval((parent as JSONArrayPanel).array)
                        else JSONObjectTooltip(obj, editor)
                    t.show(this@JSONObjectPanel, e.x, e.y)
                }
            }

            override fun mouseEntered(e: MouseEvent?) {
                background = Color(126, 144, 191)
            }

            override fun mouseExited(e: MouseEvent?) {
                background = Color.LIGHT_GRAY
            }
        })
    }
}