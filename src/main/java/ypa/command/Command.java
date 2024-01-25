package ypa.command;

import java.util.Collection;
import java.util.HashSet;

import ypa.model.KSCell;

/**
 * Abstract base class to represent an executable and revertible command,
 * applicable to a receiving object.
 * Concrete command classes extend this base class,
 * by adding parameters (when needed), revert state (when needed), and
 * overriding execute() and revert().
 *
 * This base class can already be used as a command that does nothing.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public abstract class Command {

    /** Execution state. */
    private boolean executed;

    /**
     * Constructs a command for a given receiver.
     *
     * @param executed  initial execution state
     */
    public Command(final boolean executed) {
        this.executed = executed;
    }

    /**
     * Gets execution status of this command.
     *
     * @return execution status
     */
    public boolean isExecuted() {
        return executed;
    }

    /**
     * Executes the command.
     * A concrete command will override this method.
     *
     * @throws IllegalStateException  if {@code executed}
     * @pre {@code ! executed && }
     *   precondition of the command holds in the receiver
     * @post {@code executed}
     */
    public void execute() throws IllegalStateException {
        if (executed) {
            throw new IllegalStateException(getClass().getSimpleName()
                    + ".execute().pre failed: command was already executed");
        }
        executed = true;
    }

    /**
     * Reverts the command.
     * A concrete command will override this method.
     *
     * @pre {@code executed && }
     *   precondition of the reversal holds in the receiver
     * @throws IllegalStateException  if {@code ! executed}
     * @post {@code ! executed}
     */
    public void revert() throws IllegalStateException {
        if (!executed) {
            throw new IllegalStateException(getClass().getSimpleName()
                    + ".revert().pre failed: command was not yet executed");
        }
        executed = false;
    }

    /**
     * Gets all the cells involved in this command.  Must be overridden.
     *
     * @return collection of all cells involved in this command
     */
    public Collection<KSCell> getCells() {
        return new HashSet<>();
    }

}
