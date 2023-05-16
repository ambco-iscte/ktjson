import model.elements.JSONArray
import model.elements.JSONNumber
import model.elements.JSONObject
import model.elements.JSONProperty
import mvc.commands.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestCommands {

    private val commands: Stack<Command> = Stack()

    private fun execute(command: Command) {
        command.execute()
        commands.push(command)
    }

    private fun undo() {
        if (!commands.isEmpty()) commands.pop().undo()
    }

    @Test
    fun testAddObjectProperty() {
        val obj = JSONObject.empty()
        assertFalse(obj.hasProperty("myKey"))
        execute(AddObjectProperty(obj, JSONProperty("myKey", JSONNumber(0))))
        assertTrue(obj.hasProperty("myKey"))
        undo()
        assertFalse(obj.hasProperty("myKey"))
    }

    @Test
    fun testRemoveObjectProperty() {
        val obj = JSONObject(mutableListOf(JSONProperty("myKey", JSONNumber(0))))
        assertTrue(obj.hasProperty("myKey"))
        execute(RemoveObjectProperty(obj, "myKey"))
        assertFalse(obj.hasProperty("myKey"))
        undo()
        assertTrue(obj.hasProperty("myKey"))
    }

    @Test
    fun testUpdateObjectProperty() {
        val obj = JSONObject(mutableListOf(JSONProperty("myKey", JSONNumber(0))))
        assertEquals(JSONNumber(0), obj.getProperty("myKey"))
        execute(UpdateObjectProperty(obj, "myKey", JSONNumber(1)))
        assertEquals(JSONNumber(1), obj.getProperty("myKey"))
        undo()
        assertEquals(JSONNumber(0), obj.getProperty("myKey"))
    }

    @Test
    fun testAddArrayElement() {
        val arr = JSONArray(mutableListOf())
        val element = JSONNumber(0)
        assertFalse(arr.contains(element))
        execute(AddArrayElement(arr, element))
        assertTrue(arr.contains(element))
        undo()
        assertFalse(arr.contains(element))
    }

    @Test
    fun testRemoveArrayElement() {
        val element = JSONNumber(0)
        val arr = JSONArray(mutableListOf(element))
        assertTrue(arr.contains(element))
        execute(RemoveArrayElement(arr, element))
        assertFalse(arr.contains(element))
        undo()
        assertTrue(arr.contains(element))
    }

    @Test
    fun testSetArrayElement() {
        val arr = JSONArray(mutableListOf(JSONNumber(0)))
        assertEquals(JSONNumber(0), arr.get(0))
        execute(SetArrayElement(arr, 0, JSONNumber(1)))
        assertEquals(JSONNumber(1), arr.get(0))
        undo()
        assertEquals(JSONNumber(0), arr.get(0))
    }

    @Test
    fun testReplaceArrayElement() {
        val arr = JSONArray(mutableListOf(JSONNumber(0)))
        assertEquals(JSONNumber(0), arr.get(0))
        execute(ReplaceArrayElement(arr, JSONNumber(0), JSONNumber(1)))
        assertEquals(JSONNumber(1), arr.get(0))
        undo()
        assertEquals(JSONNumber(0), arr.get(0))
    }
}