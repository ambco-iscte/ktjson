package mvc

import Stack
import model.elements.*
import mvc.commands.*
import mvc.editor.JSONEditor
import mvc.editor.JSONEditorListener

fun main() {
    // Example JSON object
    val json = JSONObject(mutableListOf(
        JSONProperty("name", JSONString("ktjson")),
        JSONProperty("language", JSONString("kotlin")),
        JSONProperty("version", JSONNumber(1.0)),
        JSONProperty("repo", JSONString("github.com/ambco-iscte/ktjson")),
        JSONProperty("author", JSONObject(mutableListOf(
            JSONProperty("name", JSONString("Afonso Caniço")),
            JSONProperty("affiliation", JSONString("Iscte-IUL")),
            JSONProperty("isNerd", JSONBoolean(true)),
            JSONProperty("interests", JSONArray(mutableListOf(
                JSONString("programming"),
                JSONString("kotlin"),
                JSONString("json")
            )))
        )))
    ))

    // JSON Editor GUI
    val editor = JSONEditor(json)

    // Command stack
    val commands = Stack<Command>()
    val undone = Stack<Command>() // Allows for easy re-do

    // Auxiliary function to execute and store commands
    fun execute(command: Command) {
        command.execute()
        commands.push(command)
    }

    // Auxiliary function to undo and store undone command
    fun undo(command: Command) {
        command.undo()
        undone.push(command)
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
            if (!commands.isEmpty()) undo(commands.pop())
        }

        override fun onRedo() {
            if (!undone.isEmpty()) execute(undone.pop())
        }
    })

    // Open the editor window
    editor.open()
}