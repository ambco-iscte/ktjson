import model.elements.*
import model.listeners.JSONArrayListener
import model.listeners.JSONObjectListener
import kotlin.test.Test
import kotlin.test.assertEquals

class TestListeners {

    @Test
    fun testObjectListener() {
        val obj = JSONObject.empty()

        var added = 0
        var removed = 0
        var updated = 0

        obj.addListener(object : JSONObjectListener {
            override fun onPropertyAdded(property: JSONProperty) { added++ }
            override fun onPropertyRemoved(property: JSONProperty) { removed++ }
            override fun onPropertyUpdated(old: JSONProperty, new: JSONProperty) { updated++ }
        })

        obj.addProperty("myKey1", JSONNumber(0))
        obj.addProperty("myKey2", JSONNumber(1))
        obj.addProperty("myKey3", JSONNumber(2))

        obj.setProperty("myKey1", JSONString("hello"))
        obj.setProperty("myKey2", JSONString("world"))
        obj.setProperty("myKey3", JSONString(":)"))

        obj.removeProperty("myKey1")
        obj.removeProperty("myKey2")
        obj.removeProperty("myKey3")

        assertEquals(3, added)
        assertEquals(3, removed)
        assertEquals(3, updated)
    }

    @Test
    fun testArrayListener() {
        val array = JSONArray(mutableListOf())

        var added = 0
        var removed = 0
        var set = 0

        array.addListener(object : JSONArrayListener {
            override fun onElementAdded(element: JSONElement) { added++ }
            override fun onElementRemoved(index: Int, element: JSONElement) { removed++ }
            override fun onElementSet(index: Int, element: JSONElement) { set++ }
        })

        array.add(JSONNumber(0))
        array.add(JSONNumber(1))
        array.add(JSONNumber(2))

        array.set(0, JSONString("hello"))
        array.set(1, JSONString("world"))
        array.set(2, JSONString(":)"))

        array.removeAt(0)
        array.removeAt(0)
        array.removeAt(0)

        assertEquals(3, added)
        assertEquals(3, removed)
        assertEquals(3, set)
    }
}