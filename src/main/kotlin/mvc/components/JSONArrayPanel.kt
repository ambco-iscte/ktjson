package mvc.components

import model.elements.JSONArray
import model.elements.JSONElement
import model.listeners.JSONArrayListener
import mvc.JSONEditor
import mvc.components.tooltips.JSONArrayTooltip
import mvc.swingComponentFromJSONElement
import java.awt.Color
import java.awt.Dimension
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*

// GUI panel that displays a JSONArray.
internal data class JSONArrayPanel(val array: JSONArray, val editor: JSONEditor): JPanel() {
    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        border = BorderFactory.createEmptyBorder(10, 10, 0, 10)
        background = Color.GRAY

        // Listen for changes on the model and update the GUI accordingly.
        array.addListener(object : JSONArrayListener {
            override fun onElementAdded(element: JSONElement) {
                add(swingComponentFromJSONElement(element, editor))
                add(Box.createRigidArea(Dimension(0, 10)))
                revalidate()
                repaint()
                editor.refresh()
            }

            override fun onElementRemoved(index: Int, element: JSONElement) {
                val indexToRemove = getActualIndex(index)
                remove(indexToRemove) // Remove the actual element
                remove(indexToRemove) // Remove the empty separator (makes it look nice and pretty).
                revalidate()
                repaint()
                editor.refresh()
            }

            override fun onElementSet(index: Int, element: JSONElement) {
                val indexToReplace = getActualIndex(index)
                remove(indexToReplace)
                add(swingComponentFromJSONElement(element, editor), indexToReplace)
                revalidate()
                repaint()
                editor.refresh()
            }
        })

        // Add panels to display every element already in the array.
        array.getElements().forEach { element ->
            add(swingComponentFromJSONElement(element, editor))
            add(Box.createRigidArea(Dimension(0, 10)))
        }

        // Add a mouse listener for nice highlights and to display the tooltip when the panel is right-clicked.
        addMouseListener(object : MouseListener {
            override fun mousePressed(e: MouseEvent?) { }
            override fun mouseReleased(e: MouseEvent?) { }

            override fun mouseClicked(e: MouseEvent?) {
                if (e != null && SwingUtilities.isRightMouseButton(e)) {
                    val tooltip =
                        if (parent != null && parent is JSONArrayPanel)
                            JSONArrayTooltip(array, editor).withArrayRemoval((parent as JSONArrayPanel).array)
                        else JSONArrayTooltip(array, editor)
                    tooltip.show(this@JSONArrayPanel, e.x, e.y)
                }
            }

            override fun mouseEntered(e: MouseEvent?) {
                background = Color(71, 94, 128)
            }

            override fun mouseExited(e: MouseEvent?) {
                background = Color.GRAY
            }
        })
    }

    // Since the components of the array panel include both the proper elements and empty separators,
    // the indices of an element in the array and of its corresponding panel in this panel's components
    // do not match. As such, to get the actual index of an element in the array from the index of the element's
    // corresponding panel in this panel's components, we must get the index in *only* the non-separator components.
    private fun getActualIndex(index: Int): Int = components.indexOf(
        components.filter { it is JSONObjectPanel || it is PrimitiveJSONElementPanel }[index]
    )
}

