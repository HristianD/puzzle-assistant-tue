package ypa.solvers;

import java.util.Collection;
import java.util.Stack;

import ypa.command.Command;
import ypa.model.KSPuzzle;

/**
 * Abstract base class for solvers of Killer Sudoku puzzles.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public abstract class AbstractSolver {

    /** The puzzle being solved. */
    protected KSPuzzle puzzle;

    /** Commands executed. */
    protected Stack<Command> commands;

    /**
     * Constructs a reasoner for a given puzzle.
     *
     * @param puzzle  the puzzle
     * @throws IllegalArgumentException  if {@code puzzle == null}
     * @pre {@code puzzle != null}
     */
    public AbstractSolver(final KSPuzzle puzzle) {
        if (puzzle == null) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "().pre failed: puzzle == null");
        }
        this.puzzle = puzzle;
        commands = new Stack<>();
    }

    /**
     * Gets the commands whose execution led to current puzzle state.
     *
     * @return commands executed to get to current puzzle state
     */
    public Collection<Command> getCommands() {
        return commands;
    }

    /**
     * Either finds one solution of the puzzle from its current state,
     * if solvable, or leaves the puzzle unchanged.
     *
     * @return whether puzzle was solved
     * @pre {@code puzzle != null}
     * @modifies {@code puzzle}
     * @post {@code
     *      (\result && puzzle.isSolved()) || (! \result && puzzle unchanged)}
     */
    public abstract boolean solve();

}
