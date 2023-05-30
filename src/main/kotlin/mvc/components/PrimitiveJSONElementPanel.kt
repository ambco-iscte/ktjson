package mvc.components

import model.elements.JSONBoolean
import model.elements.JSONElement
import mvc.components.tooltips.JSONEditorTooltip
import mvc.editor.JSONEditor
import java.awt.Component
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JCheckBox
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingUtilities

// GUI panel that displays a primitive/literal JSONElement (null, string, number, boolean).
internal data class PrimitiveJSONElementPanel(val element: JSONElement, val editor: JSONEditor): JPanel() {
    init {
        layout = GridLayout(1, 1)
        isOpaque = false
        maximumSize = Dimension(maximumSize.width, 30)
        minimumSize = Dimension(minimumSize.width, maximumSize.height)
        when (element) {
            // If the element is a boolean, it can be displayed as a checkbox.
            is JSONBoolean -> {
                val checkbox = JCheckBox().apply {
                    isOpaque = false
                    isSelected = element.boolean
                    addItemListener { notify(JSONBoolean(isSelected)) }
                    listen(this)
                }
                add(checkbox)
            }
            // Otherwise, it can be represented as a simple text field.
            else -> {
                val text = JTextField(element.toString().trimStart().removeSurrounding("\"")).apply {
                    addKeyListener(object : KeyAdapter() {
                        override fun keyPressed(e: KeyEvent?) {
                            if (e != null && e.keyCode == KeyEvent.VK_ENTER) notify(JSONElement.parseLiteral(text))
                        }
                    })
                    listen(this)
                }
                add(text)
            }
        }
    }

    // Notifies the editor of a change in the element's value.
    private fun notify(new: JSONElement) {
         if (parent.parent != null && parent.parent is JSONPropertyPanel) {
            val property = (parent.parent as JSONPropertyPanel).property
            if (parent.parent.parent is JSONObjectPanel)
                editor.onObjectPropertyChanged((parent.parent.parent as JSONObjectPanel).obj, property.key, new)
        }
        else if (parent is JSONArrayPanel)
            editor.onArrayElementReplaced((parent as JSONArrayPanel).array, element, new)
    }

    // Adds a tooltip right-click listener to a component.
    private fun listen(component: Component) = component.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent?) {
            val p = this@PrimitiveJSONElementPanel.parent
            if (p != null && p is JSONArrayPanel) {
                val tooltip = JSONEditorTooltip(element, editor).withArrayRemoval(p.array)
                if (e != null && SwingUtilities.isRightMouseButton(e))
                    tooltip.show(this@PrimitiveJSONElementPanel, e.x, e.y)
            }
        }
    })
}