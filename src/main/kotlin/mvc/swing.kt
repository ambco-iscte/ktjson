package mvc

import model.elements.JSONArray
import model.elements.JSONElement
import model.elements.JSONObject
import model.elements.JSONProperty
import mvc.components.JSONArrayPanel
import mvc.components.JSONObjectPanel
import mvc.components.JSONPropertyPanel
import mvc.components.PrimitiveJSONElementPanel
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JScrollPane

/**
 * Wraps a Java Swing [Component] in a [JScrollPane] with an empty border.
 */
internal fun Component.asScrollable(): JScrollPane = JScrollPane(this).apply {
    horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    border = BorderFactory.createEmptyBorder()
}

/**
 * Creates a [JSONEditor] panel to appropriately store a [JSONElement].
 * @param element The [JSONElement] to display.
 * @param editor The [JSONEditor] to attach the created panel to.
 */
internal fun swingComponentFromJSONElement(element: JSONElement, editor: JSONEditor): JComponent = when (element) {
    is JSONObject -> JSONObjectPanel(element, editor)
    is JSONArray -> JSONArrayPanel(element, editor).asScrollable().apply {
        maximumSize = Dimension(maximumSize.width, 300)
        minimumSize = Dimension(minimumSize.width, 150)
        border = BorderFactory.createLineBorder(Color.DARK_GRAY, 3, false)
        background = Color.GRAY
    }
    is JSONProperty -> JSONPropertyPanel(element, editor)
    else -> PrimitiveJSONElementPanel(element, editor)
}