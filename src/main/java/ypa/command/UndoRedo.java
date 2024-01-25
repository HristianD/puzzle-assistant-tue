package ypa.command; // <<<<< TODO: Comment this line out when submitting to Momotor! // <<<<< TODO: Comment this line out when submitting to Momotor!

import java.util.Stack;

/**
 * Facilities for an undo-redo mechanism, on the basis of commands.
 *
<!--//# BEGIN TODO: Names, student IDs, group name, and date-->
Kaloyan Milev, 1815822
Hristiyan Dimitrov, 1802305
Rob Borsboom, 1821369
Sophie Tjin-A-Ton, 1913840
Assignment G2 2023Q2 18, 20.01.2024
<!--//# END TODO-->
 */
public class UndoRedo {

//# BEGIN TODO: Representation in terms of instance variables, incl. rep. inv.
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
//# END TODO

    /**
     * Returns whether an {@code undo} is possible.
     *
     * @return whether {@code undo} is possible
     */
    public boolean canUndo() {
        //# BEGIN TODO: Implementation of canUndo
        return !undoStack.isEmpty();
        //# END TODO
    }

    /**
     * Returns whether a {@code redo} is possible.
     *
     * @return {@code redo().pre}
     */
    public boolean canRedo() {
        //# BEGIN TODO: Implementation of canRedo
        return !redoStack.isEmpty();
        //# END TODO
    }

    /**
     * Returns command most recently done.
     *
     * @return command at top of undo stack
     * @throws IllegalStateException if precondition failed
     * @pre {@code canUndo()}
     */
    public Command lastDone() throws IllegalStateException {
        //# BEGIN TODO: Implementation of lastDone
        if (!canUndo()) {
            throw new IllegalStateException("No command to undo");
        }
        return undoStack.peek();
        //# END TODO
    }

    /**
     * Returns command most recently undone.
     *
     * @return command at top of redo stack
     * @throws IllegalStateException if precondition failed
     * @pre {@code canRedo()}
     */
    public Command lastUndone() throws IllegalStateException {
        //# BEGIN TODO: Implementation of lastUndone
        if (!canRedo()) {
            throw new IllegalStateException("No command to redo");
        }
        return redoStack.peek();
        //# END TODO
    }

    /**
     * Clears all undo-redo history.
     *
     * @modifies {@code this}
     */
    public void clear() {
        //# BEGIN TODO: Implementation of clear
        undoStack.clear();
        redoStack.clear();
        //# END TODO
    }

    /**
     * Adds given command to the do-history.
     * If the command was not yet executed, then it is first executed.
     *
     * @param command the command to incorporate
     * @modifies {@code this}
     */
    public void did(final Command command) {
        //# BEGIN TODO: Implementation of did
        if (!command.isExecuted()) {
            command.execute();
        }
        undoStack.push(command);
        redoStack.clear();
        //# END TODO
    }

    /**
     * Undo the most recently done command, optionally allowing it to be redone.
     *
     * @param redoable whether to allow redo
     * @throws IllegalStateException if precondition violated
     * @pre {@code canUndo()}
     * @modifies {@code this}
     */
    public void undo(final boolean redoable) throws IllegalStateException {
        //# BEGIN TODO: Implementation of undo
        if (!canUndo()) {
            throw new IllegalStateException("No command to undo");
        }
        Command command = undoStack.pop();
        command.revert();
        if (redoable) {
            redoStack.push(command);
        }
        //# END TODO
    }

    /**
     * Redo the most recently undone command.
     *
     * @throws IllegalStateException if precondition violated
     * @pre {@code canRedo()}
     * @modifies {@code this}
     */
    public void redo() throws IllegalStateException {
        //# BEGIN TODO: Implementation of redo
        if (!canRedo()) {
            throw new IllegalStateException("No command to redo");
        }
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
        //# END TODO
    }

    /**
     * Undo all done commands.
     *
     * @param redoable whether to allow redo
     * @modifies {@code this}
     */
    public void undoAll(final boolean redoable) {
        //# BEGIN TODO: Implementation of undoAll
        while (canUndo()) {
            undo(redoable);
        }
        //# END TODO
    }

    /**
     * Redo all undone commands.
     *
     * @modifies {@code this}
     */
    public void redoAll() {
        //# BEGIN TODO: Implementation of redoAll
        while (canRedo()) {
            redo();
        }
        //# END TODO
    }

}

