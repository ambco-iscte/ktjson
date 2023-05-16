package mvc.components

import model.elements.JSONProperty
import mvc.JSONEditor
import mvc.components.tooltips.JSONPropertyTooltip
import mvc.swingComponentFromJSONElement
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*

// GUI panel that displays a JSONProperty.
internal data class JSONPropertyPanel(val property: JSONProperty, val editor: JSONEditor): JPanel() {
    init {
        layout = BoxLayout(this, BoxLayout.X_AXIS)
        border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
        background = Color.LIGHT_GRAY

        // Add a label to display the property key.
        add(JLabel(property.key).apply {
            isOpaque = false
            minimumSize = Dimension(150, 30)
            preferredSize = minimumSize
        })

        // Add a panel to display the property's value.
        add(JPanel().apply {
            add(swingComponentFromJSONElement(property.value, editor))
            layout = GridLayout()
            isOpaque = false
        })

        // Add a mouse listener for nice highlights to display the tooltip when the panel is right-clicked.
        addMouseListener(object : MouseListener {
            override fun mousePressed(e: MouseEvent?) { }
            override fun mouseReleased(e: MouseEvent?) { }

            override fun mouseClicked(e: MouseEvent?) {
                if (e != null && SwingUtilities.isRightMouseButton(e)) {
                    val tooltip = JSONPropertyTooltip((parent as JSONObjectPanel).obj, property, editor)
                    tooltip.show(this@JSONPropertyPanel, e.x, e.y)
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

