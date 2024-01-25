package ypa.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ypa.model.KSCell;

/**
 * A compound command consists of a sequence of commands.
 * Commands can be added one by one to the compound command.
 * All added commands must have the same execution status as
 * the compound command.
 * The client can select the initial execution status in the constructor.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class CompoundCommand extends Command {

    /** The sequence of commands. */
    private final List<Command> sequence;

    public CompoundCommand() {
        this(false);
    }


    /**
     * Constructs a compound command.
     *
     * @param executed the initial execution status
     */
    public CompoundCommand(final boolean executed) {
        super(executed);
        sequence = new ArrayList<>();
    }

    /**
     * Adds a given command at the end of the sequence,
     * ignoring {@code null} commands.
     * A {@code CompoundCommand} is just added "as is".
     *
     * @param command  the command to add
     * @throws IllegalStateException  if execution state of added command
     *   does not match that of this compound command
     * @pre {@code command != null \implies
     *   this.isExecuted() == command.isExecuted()}
     */
    public void add(final Command command) throws IllegalStateException {
        if (command == null) {
            // ignore
            return;
        }
        // check execution status of command
        if (isExecuted() != command.isExecuted()) {
            throw new IllegalStateException(getClass().getSimpleName()
                    + ".add().pre failed: this.executed == " + isExecuted() + " != "
                    + command.isExecuted() + " == command.executed");
        }
        sequence.add(command);
    }

    /**
     * Adds the commands in a given compound command at the end of the sequence.
     *
     * @param compound  the compound command to add
     * @throws IllegalArgumentException  if {@code compound == null}
     * @throws IllegalStateException  if execution state of {@code compound}
     *   does not match that of this compound command
     * @pre {@code compound != null &&
     *   this.isExecuted() == compound.isExecuted()}
     */
    public void addAll(final CompoundCommand compound)
            throws IllegalArgumentException, IllegalStateException {
        if (compound == null) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + ".addAll(): command == null");
        }
        if (isExecuted() != compound.isExecuted()) {
            throw new IllegalStateException(getClass().getSimpleName()
                    + ".addAll().pre failed: this.executed == " + isExecuted() + " != "
                    + compound.isExecuted() + " == command.executed");
        }
        sequence.addAll(compound.sequence);
    }

    /**
     * Gets number of commands in this command.
     *
     * @return number of commands in this
     */
    public int size() {
        return sequence.size();
    }

    @Override
    public void execute() {
        if (size() != 0) {
            super.execute();
        }
        for (final Command command : sequence) {
            command.execute();
        }
    }

    @Override
    public void revert() {
        if (size() != 0) {
            super.revert();
        }
        // Revert commands in reverse order
        for (int i = sequence.size() - 1; 0 <= i; --i) {
            final Command command = sequence.get(i);
            command.revert();
        }
    }

    /**
     * Gets all the cells involved in the commands of this.
     *
     * @return collection of cells involved in this command
     */
    @Override
    public Collection<KSCell> getCells() {
        final Collection<KSCell> result = super.getCells();
        for (final Command command : sequence) {
            result.addAll(command.getCells());
        }
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + this.size() + " operations)";
    }

}
