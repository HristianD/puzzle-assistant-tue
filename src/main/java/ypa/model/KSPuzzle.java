package ypa.model;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.util.Scanner;

/**
 * A Killer Sudoku instance derived from the puzzle file. Each puzzle has a grid with cells,
 * which have values. This class acts as a facade for the underlying classes. 
 */
public class KSPuzzle {
    /** The puzzle's (file) name. */
    private String name;

    /** The possible modes of a puzzle. */
    public enum Mode {
        /** When puzzle can be edited. */
        EDIT,
        /** When puzzle can be solved, but not edited. */
        SOLVE,
        /** When puzzle can only be viewed, but not changed. */
        VIEW
    }

    /** Smallest number allowed. */
    private int minNumber = 1;

    /** Largest number allowed. */
    private int maxNumber = 9;

    /** The puzzle's mode. */
    private Mode mode;

    /** The grid of cells. */
    private final KSGrid grid;

    /**
     * Constructs a new puzzle with initial state read from given scanner,
     * and with a given name.
     *
     * @param scanner  the given scanner
     * @param name  the given name
     */
    public KSPuzzle(final Scanner scanner, final String name) {
        this.name = name;
        this.mode = Mode.VIEW;
        this.grid = new KSGrid(scanner);
    }

    /**
     * Get puzzle's grid.
     * 
     * @return {@code grid.getMatrix()}
     */
    public final KSCell[][] getMatrix() {
        return this.grid.getMatrix();
    }

    /**
     * Checks whether after setting a cell to a certain value (state)
     * the puzzle is a valid instance of a Killer Sudoku.
     * 
     * @param cell the last modified cell
     * @return {@code true} if no rules are violated, {@code false} otherwise
     */
    public boolean isValid(final KSCell cell) {
        return this.grid.isValidPuzzleInstance(cell);
    }
    
    public boolean isSolved(final KSCell cell) {
        return this.grid.isValidPuzzleInstance(cell) && this.grid.isFull();
    }
    
    public void clear() {
        this.grid.clear();
    }
    
    /**
     * Gets the file name of the puzzle.
     * 
     * @return file name of the puzzle
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this puzzle. Only allowed in edit mode.
     *
     * @param name the new name
     * @throws IllegalStateException if not in edit mode
     * @pre puzzle is in edit mode
     */
    public void setName(String name) {
        if (mode != Mode.EDIT) {
            throw new IllegalStateException(this.getClass().getSimpleName()
                    + ".setName(): not in EDIT mode");
        }
        this.name = name;
    }

    /**
     * Gets the current mode of this puzzle.
     *
     * @return mode of this
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Sets the mode of this puzzle.
     *
     * @param mode the new mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Gets the smallest number allowed in a cell.
     *
     * @return the smallest number allowed in a cell
     */
    public int getMinNumber() {
        return minNumber;
    }

    /**
     * Gets the largest number allowed in a cell.
     *
     * @return the largest number allowed in a cell
     */
    public int getMaxNumber() {
        return maxNumber;
    }
    
    @Override
    public String toString() {
        return grid.toString();
    }
    
    /**
     * Gets the number of columns in this puzzle.
     *
     * @return number of columns
     */
    public int getColumnCount() {
        return KSGrid.COLS;
    }

    /**
     * Gets the number of rows in this puzzle.
     *
     * @return number of rows
     */
    public int getRowCount() {
        return KSGrid.ROWS;
    }
    
    /**
     * Returns whether a coordinate pair is in the puzzle's grid.
     *
     * @param rowIndex  the row index
     * @param columnIndex  the column index
     * @return whether {@code (row, column)} belongs to the grid
     */
    public boolean has(final int rowIndex, final int columnIndex) {
        return 0 <= rowIndex && rowIndex < this.getRowCount()
                && 0 <= columnIndex && columnIndex < this.getColumnCount();
    }
    
    /**
     * Gets the cell at given coordinates.
     *
     * @param rowIndex  the row coordinate to get from
     * @param columnIndex  the column coordinate to get from
     * @return cell at {@code rowIndex, columnIndex}
     * @pre {@code 0 <= rowIndex < getRows() &&
     *   0 <= columnIndex < getColumns()}
     * @post {@code \result = cells[rowIndex, columnIndex]}
     */
    public KSCell getCell(final int rowIndex, final int columnIndex) {
        return grid.getCell(rowIndex, columnIndex);
    }
}