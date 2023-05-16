internal class Stack<T> : Iterable<T> {
    private val list: MutableList<T> = mutableListOf()

    fun isEmpty(): Boolean = list.isEmpty()

    fun size(): Int = list.size

    fun push(element: T) = list.add(element)

    fun pop(): T = if (isEmpty()) throw IllegalStateException("Stack underflow!") else list.removeLast()

    fun peek(): T = if (isEmpty()) throw NoSuchElementException("Stack is empty!") else list.last()

    override fun iterator(): Iterator<T> = list.iterator()
}