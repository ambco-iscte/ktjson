package mvc

import Stack
import model.elements.JSONArray
import model.elements.JSONElement
import model.elements.JSONObject
import model.elements.JSONProperty
import mvc.commands.*
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

/**
 * Listens for actions performed on [JSONEditor]s.
 */
interface JSONEditorListener {
    /**
     * Called when the Undo button is pressed on the [JSONEditor].
     */
    fun onUndo() { }

    /**
     * Called when the [JSONEditor] is used to remove a [JSONProperty] from a [JSONObject].
     * @param obj The object from which the property was removed.
     * @param property The property that was removed.
     */
    fun onObjectPropertyRemoved(obj: JSONObject, property: JSONProperty) { }

    /**
     * Called when the [JSONEditor] is used to add a [JSONProperty] to a [JSONObject].
     * @param obj The object to which the property was added.
     * @param key The key of the property that was added.
     * @param value The value of the property that was added.
     */
    fun onObjectPropertyAdded(obj: JSONObject, key: String, value: JSONElement) { }

    /**
     * Called when the [JSONEditor] is used to update the value of a [JSONObject]'s [JSONProperty].
     * @param obj The object in which the property value was updated.
     * @param key The key of the property which was updated.
     * @param value The new value of the property.
     */
    fun onObjectPropertyChanged(obj: JSONObject, key: String, value: JSONElement) { }

    /**
     * Called when the [JSONEditor] is used to add a [JSONElement] to a [JSONArray].
     * @param array The array to which the element was added.
     * @param element The element that was added.
     */
    fun onArrayElementAdded(array: JSONArray, element: JSONElement) { }

    /**
     * Called when the [JSONEditor] is used to remove a [JSONElement] from a [JSONArray].
     * @param array The array from which the element was removed.
     * @param element The element that was removed.
     */
    fun onArrayElementRemoved(array: JSONArray, element: JSONElement) { }

    /**
     * Called when the [JSONEditor] is used to replace a [JSONElement] in a [JSONArray] with a new element.
     * @param array The array whose element was replaced.
     * @param old The element that was replaced.
     * @param new The new element.
     */
    fun onArrayElementReplaced(array: JSONArray, old: JSONElement, new: JSONElement) { }
}

fun main() {
    // Empty JSON object
    val json = JSONObject.empty()

    // JSON Editor GUI
    val editor = JSONEditor(json)

    // Command stack
    val commands = Stack<Command>()

    // Auxiliary function to execute and store commands
    fun execute(command: Command) {
        command.execute()
        commands.push(command)
    }

    // Add view listener that actually modifies the model through commands
    editor.addListener(object : JSONEditorListener {
        // JSONProperty value changed in JSONObject
        override fun onObjectPropertyChanged(obj: JSONObject, key: String, value: JSONElement) =
            execute(UpdateObjectProperty(obj, key, value))

        // JSONProperty removed from JSONObject
        override fun onObjectPropertyRemoved(obj: JSONObject, property: JSONProperty) =
            execute(RemoveObjectProperty(obj, property.key))

        // JSONProperty added to JSONObject
        override fun onObjectPropertyAdded(obj: JSONObject, key: String, value: JSONElement) =
            execute(AddObjectProperty(obj, JSONProperty(key, value)))

        // JSONElement added to JSONArray
        override fun onArrayElementAdded(array: JSONArray, element: JSONElement) =
            execute(AddArrayElement(array, element))

        // JSONElement removed from JSONArray
        override fun onArrayElementRemoved(array: JSONArray, element: JSONElement) =
            execute(RemoveArrayElement(array, element))

        // JSONElement in JSONArray replaced by another
        override fun onArrayElementReplaced(array: JSONArray, old: JSONElement, new: JSONElement) =
            execute(ReplaceArrayElement(array, old, new))

        override fun onUndo() {
            if (!commands.isEmpty()) commands.pop().undo()
        }
    })

    // Open the editor window
    editor.open()
}