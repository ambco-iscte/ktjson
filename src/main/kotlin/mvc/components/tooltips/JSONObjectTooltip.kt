package mvc.components.tooltips

import model.elements.JSONArray
import model.elements.JSONElement
import model.elements.JSONObject
import mvc.editor.JSONEditor
import javax.swing.*

// Tooltip shown when a JSONObjectPanel is right-clicked.
internal data class JSONObjectTooltip(val obj: JSONObject, override val editor: JSONEditor): JSONEditorTooltip(obj, editor) {
    init {
        add(JMenuItem("Add Literal Property").apply {
            addActionListener {
                val keyField = JTextField(10)
                val valueField = JTextField(10)

                val panel = JPanel().apply {
                    add(JLabel("Key:"))
                    add(keyField)
                    add(Box.createHorizontalStrut(15))
                    add(JLabel("Value:"))
                    add(valueField)
                }

                val result = JOptionPane.showConfirmDialog(
                    null, panel,
                    "Add JSON Data Property", JOptionPane.OK_CANCEL_OPTION
                )

                if (result == JOptionPane.OK_OPTION)
                    editor.onPropertyAdded(obj, keyField.text, JSONElement.parseLiteral(valueField.text))
            }
        })

        add(JMenuItem("Add Composite Property").apply {
            addActionListener {
                val keyField = JTextField(20)
                val type = JComboBox<String>(arrayOf("Object", "Array"))

                val panel = JPanel().apply {
                    add(JLabel("Key:"))
                    add(keyField)
                    add(Box.createHorizontalStrut(15))
                    add(JLabel("Type:"))
                    add(type)
                }

                val result = JOptionPane.showConfirmDialog(
                    null, panel,
                    "Add JSON Composite Property", JOptionPane.OK_CANCEL_OPTION
                )

                if (result == JOptionPane.OK_OPTION)
                    editor.onPropertyAdded(
                        obj,
                        keyField.text,
                        if (type.selectedIndex == 0) JSONObject.empty() else JSONArray.empty()
                    )
            }
        })
    }
}