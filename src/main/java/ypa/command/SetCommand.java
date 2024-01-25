package ypa.command;

import java.util.Collection;

import ypa.model.KSCell;

/**
 * The command to set the state of a cell.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class SetCommand extends GenericCommand<KSCell> {

// Representation of command state
    /** The command's parameter. */
    private final int newState;

    /** Previous state of the receiver, for revert(). */
    private int oldState;
//

    /**
     * Constructs a set command for a given receiver and new state.
     *
     * @param receiver  the given receiver
     * @param newState  the new state
     */
    public SetCommand(final KSCell receiver, final int newState) {
        super(receiver);
// Initialize command state
        this.newState = newState;
//
    }

// Operations
    @Override
    public void execute() {
        super.execute();
        oldState = receiver.getState(); // should not be done in constructor!
        receiver.setState(newState);
    }

    @Override
    public void revert() {
        super.revert();
        receiver.setState(oldState);
    }

    @Override
    public Collection<KSCell> getCells() {
        Collection<KSCell> result = super.getCells();
        result.add(receiver);
        return result;
    }
//

}
