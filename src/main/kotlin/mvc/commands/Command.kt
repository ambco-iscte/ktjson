package mvc.commands

/**
 * Generic command interface.
 */
interface Command {
    /**
     * Executes the command.
     */
    fun execute()

    /**
     * Undoes the effect of the command.
     */
    fun undo()
}