package ypa.reasoning;

import ypa.command.CompoundCommand;
import ypa.model.KSPuzzle;

/**
 * Base class for reasoning strategies (reasoners).
 * A reasoning strategy applies to a given puzzle and fills in some
 * cells that are forced by the already given cells.
 * If the initial state was valid,
 * then the resulting state must be valid as well.
 * A reasoning strategy can also discover that it is impossible to solve
 * the puzzle from the given state (signaled by returning null).
 * <p>
 * The base class can be instantiated to get a reasoner that does nothing.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class Reasoner {

    /** The puzzle to apply the reasoner to. */
    protected KSPuzzle puzzle;

    // Initially there was, on purpose, no constructor that sets the puzzle,
    // because then every subclass must define that constructor as well.
    // The small price for this is that after construction,
    // client code must also set the puzzle.
    // Disadvantage of this is that each reasoner must be stored
    // in a variable, so that setPuzzle can be called.
    // An important question to ask is whether there is ever a need
    // to change the puzzle later.
    // If the reasoner is complex, then you may want to construct it
    // only once, and reuse it for different puzzles.
    // If you do change it later, then it would be good
    // to have the ability to set the new puzzle once for the
    // complex reasoner.
    // However, if reasoners involve cycles (which we want; possibly indirect),
    // then you have to be careful not to get into an infinite loop
    // when setting a puzzle later.

    /**
     * Constructs a reasoner for a given puzzle.
     *
     * @param puzzle  the puzzle
     * @throws IllegalArgumentException  if {@code puzzle == null}
     * @pre {@code puzzle != null}
     */
    public Reasoner(final KSPuzzle puzzle) {
        if (puzzle == null) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "().pre failed: puzzle == null");
        }
        this.puzzle = puzzle;
    }

    /**
     * Applies the reasoning strategy to the given puzzle, once.
     * There are three possible outcomes:
     * <ul>
     * <li>Reasoning led to forced commands: the returned list contains them</li>
     * <li>Reasoning led to a contradiction: the puzzle is impossible to complete,
     *      null is returned</li>
     * <li>Reasoning neither led to forced commands nor to a contradiction:
     *      the returned list is empty</li>
     * </ul>
     * To be overridden by concrete reasoner
     *
     * @return list of commands applied to cells, or {@code null} if contradiction
     * @pre {@code puzzle != null}
     * @modifies {@code puzzle}
     * @post {@code
     *      (\result == null  ==>  puzzle is not solvable and not modified) &&
     *      (\result.size() > 0  ==>  \result.isExecuted() && puzzle.isValid())}
     */
    public CompoundCommand apply() {
        return new CompoundCommand(true);
    }

}
