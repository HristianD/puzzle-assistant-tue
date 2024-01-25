package ypa.model;

import java.awt.Color;

/**
 * A class containing all necessary information for a Killer Sudoku cell.
 */
public class KSCell {
    /** The sum of all cells' values in a cage. */
    private int cageSum = 0;
    
    /** The coordinates (row and column indices) of a cell. */
    private Location location;

    /** The cell value. */
    private int value = 0;
    
    public static final int EMPTY = 0;
    
    /** String for empty state. */
    public static final String EMPTY_STR = "";

    /** The unique group number of the cage that the cell resides in. */
    private int groupNumber;

    /** The coloring of a cell. */
    private Color color = null;

    /** A (rowIndex, colIndex) tuple. Records are quite handy - 
     * instead of writing a separate class. */
    protected record Location(int rowIndex, int colIndex) {}

    /**
     * Construct a cell.
     * 
     * @param groupNumber unique cage number
     * @param rowIndex the index of the cell's row in the grid
     * @param colIndex the index of the cell's col in the grid
     */
    public KSCell(int groupNumber, int rowIndex, int colIndex) {
        this.groupNumber = groupNumber;
        location = new Location(rowIndex, colIndex);
    }

    /**
     * Get the cell's value (state).
     * 
     * @return cell state
     */
    public int getState() {
        return value;
    }

    /**
     * Sets a new cell state.
     *
     * @param state the new state
     */
    public void setState(int state) {
        this.value = state;
    }

    /**
     * Set {@code cageSum}.
     * 
     * @param cageSum the new cage sum
     */
    public void setCageSum(int cageSum) {
        this.cageSum = cageSum;
    }

    /**
     * Get the cell's cage sum.
     * 
     * @return {@code cageSum}
     */
    public int getCageSum() {
        return this.cageSum;
    }

    /**
     * Get cell's value.
     * 
     * @return {@code value}
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Get cell's location.
     * 
     * @return {@code location}
     */
    public Location getLocation() {
        return this.location;
    }
    
    /**
     * Get cell's location in string.
     * 
     * @return {@code location}
     */
    public String getLocationString() {
        return String.format("Selected cell %d, %d", this.location.rowIndex, 
                this.location.colIndex);
    }

    /**
     * Get cell's cage group.
     * 
     * @return {@code groupNumber}
     */
    public int getGroup() {
        return this.groupNumber;
    }

    /**
     * Set the cell color.
     * 
     * @param color color to be set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get cell's color.
     * 
     * @return {@code color}
     */
    public Color getColor() {
        return this.color;
    }
    
    /**
     * Checks if cell is empty.
     * @param cell cell
     * @return {@code boolean}
     */
    public boolean isEmpty(KSCell cell) {
        return cell.value == EMPTY;
    }
    
    /**
     * Puts the value in a string.
     * 
     * @return {@code string}
     */
    public String toString() {
        return switch (value) {
            case EMPTY -> EMPTY_STR;
            default -> String.valueOf(value);
        };
    }
}