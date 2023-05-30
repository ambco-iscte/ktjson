package mvc.editor

import model.elements.JSONArray
import model.elements.JSONElement
import model.elements.JSONObject
import model.elements.JSONProperty
import mvc.asScrollable
import mvc.components.JSONObjectPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener
import javax.swing.*
import javax.swing.border.CompoundBorder
import kotlin.math.max

/**
 * Java Swing application for editing [JSONObject]s.
 * @param root The initial [JSONObject] to load into the application.
 */
class JSONEditor(private val root: JSONObject) {
    private val listeners: MutableList<JSONEditorListener> = mutableListOf()

    private val srcArea = JTextArea().apply {
        isEditable = false
        tabSize = 2
        border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        font = Font("Verdana", Font.BOLD, font.size)
    }

    private val frame = JFrame("ktjson - JSON Object Editor").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        layout = GridLayout(0, 2)
        size = Dimension(600, 600)

        val left = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            border = CompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1, false),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            )
            add(JButton("Undo").apply {
                addActionListener { onUndo() }
            })
            add(Box.createRigidArea(Dimension(0, 10)))
            add(JSONObjectPanel(root, this@JSONEditor).asScrollable())
        }
        add(left)

        srcArea.text = root.toString()
        val initialSize = srcArea.font.size
        srcArea.addMouseWheelListener(object : MouseWheelListener {
            override fun mouseWheelMoved(e: MouseWheelEvent?) {
                if (e == null) return
                srcArea.font = Font("Verdana", Font.BOLD, max(initialSize, srcArea.font.size - e.wheelRotation))
            }
        })

        val right = JPanel().apply {
            layout = GridLayout()
            border = BorderFactory.createLineBorder(Color.BLACK, 1, false)
            add(srcArea.asScrollable())
        }
        add(right)
    }

    /**
     * Attaches a [JSONEditorListener] to the editor.
     */
    fun addListener(listener: JSONEditorListener) = listeners.add(listener)

    /**
     * Removes a [JSONEditorListener] from the editor.
     */
    fun removeListener(listener: JSONEditorListener) = listeners.remove(listener)

    private fun onUndo() {
        listeners.forEach { it.onUndo() }
        refresh()
    }

    // Called when the value on a JSONProperty is changed through the GUI.
    internal fun onObjectPropertyChanged(obj: JSONObject, key: String, value: JSONElement) {
        listeners.forEach { it.onObjectPropertyChanged(obj, key, value) }
        refresh()
    }

    // Called when a JSONProperty is removed from a JSONObject through the GUI.
    internal fun onPropertyRemoved(obj: JSONObject, property: JSONProperty) {
        listeners.forEach { it.onObjectPropertyRemoved(obj, property) }
        refresh()
    }

    // Called when a JSONProperty is added to a JSONObject through the GUI.
    internal fun onPropertyAdded(obj: JSONObject, key: String, value: JSONElement) {
        listeners.forEach { it.onObjectPropertyAdded(obj, key, value) }
        refresh()
    }

    // Called when a JSONElement is added to a JSONArray through the GUI.
    internal fun onArrayElementAdded(array: JSONArray, element: JSONElement) {
        listeners.forEach { it.onArrayElementAdded(array, element) }
        refresh()
    }

    // Called when a JSONElement is removed from a JSONArray through the GUI.
    internal fun onArrayElementRemoved(array: JSONArray, element: JSONElement) {
        listeners.forEach { it.onArrayElementRemoved(array, element) }
        refresh()
    }

    // Called when a JSONElement in a JSONArray is replaced by a different element through the GUI.
    internal fun onArrayElementReplaced(array: JSONArray, old: JSONElement, new: JSONElement) {
        listeners.forEach { it.onArrayElementReplaced(array, old, new) }
        refresh()
    }

    /**
     * Opens the editor window.
     */
    fun open() {
        frame.isVisible = true
    }

    // Refreshes the display.
    internal fun refresh() {
        frame.revalidate()
        frame.repaint()
        srcArea.text = root.toString()
    }
}