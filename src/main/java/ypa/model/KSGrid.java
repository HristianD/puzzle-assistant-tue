package ypa.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A class that deals with reading puzzle input and instantiating a valid implementation of it.
 */
public class KSGrid {
    /** The dimensions of the grid. */
    public static final int ROWS = 9;
    public static final int COLS = 9;

    /** The grid of cells as a list of rows. */
    private final KSCell[][] matrix;

    /** Different cage colorings. */
    private static final List<Color> COLORS = List.of(
        new Color(255, 255, 158), // light yellow
        new Color(144, 238, 144), // light green
        new Color(209, 237, 242), // pale blue
        new Color(255, 182, 193), // pink
        Color.WHITE
    );

    /** Each cage with cells is associated with a number. */
    private final Map<Integer, List<KSCell>> cageMap;

    /**
     * Constructs a grid from a given scanner.
     *
     * @param scanner the given scanner
     * @throws NullPointerException if {@code scanner == null}
     * @throws IllegalArgumentException if {@code scanner} does not yield
     *     a valid Killer Sudoku puzzle
     * @pre {@code scanner != null && matrix == null && cageMap == null}
     * and it delivers a valid puzzle grid
     * @post {@code scanner == \old(scanner)}, {@code matrix} and {@code cageMap} are initialized,
     * a valid puzzle is yielded
     */
    public KSGrid(final Scanner scanner) {
        if (scanner == null) {
            throw new NullPointerException(KSGrid.class.getSimpleName() + ".pre failed: "
                + "scanner == null");
        }

        matrix = new KSCell[ROWS][];
        cageMap = new HashMap<>();
        initializeMatrix(scanner);
    }

    /**
     * Reads from {@code scanner} and creates cells, which have values and colors and belong to
     * cages, nonets (a 3x3 grid), rows and columns. Every row, column, nonet and cage contain
     * unique numbers. Every cage has a {@code cageSum} that represents the sum of
     * all cells' values in that cage and has a color to set itself apart from neighboring cages.
     * Each puzzle read from a file is checked and therefore no invalid puzzle may
     * proceed further to the user. 
     * 
     * @param scanner the scanner input to be read from
     * @pre {@code scanner != null && matrix != null && cageMap != null}
     * @post the grid is initialized and a valid Killer Sudoku puzzle is yielded
     * @modifies {@code matrix} and {@code cageMap}
     * @throws NullPointerException if {@code scanner == null}
     * @throws IllegalArgumentException if {@code scanner} does not yield
     *     a valid Killer Sudoku puzzle
     */
    private void initializeMatrix(final Scanner scanner) {
        List<Integer> groups = new ArrayList<>();
        List<Integer> cageSums = new ArrayList<>();

        readGroupsAndCageSums(scanner, groups, cageSums);

        // initialize matrix
        for (int i = 0; i < ROWS; i++) {
            KSCell[] row = new KSCell[COLS];
            matrix[i] = row;
        }

        createCells(groups);
        setColorsAndCageSums(cageSums);
    }

    /**
     * Determines the coloring of each cell in {@code cage} according to its 
     * neighboring cages' colorings.
     * 
     * @param cage the cage whose cells' colorings are to be determined
     * @pre {@code matrix != null && COLORS != null && cell != null}
     * @post {@code \forall (cell; cage.has(cell); cell.getColor() != null)}
     * @modifies cell coloring in {@code cage}
     */
    public void determineColor(List<KSCell> cage) {
        List<Color> neighborColors = new ArrayList<>();
        Color color = Color.WHITE;
        
        for (KSCell current: cage) {
            int rowIndex = current.getLocation().rowIndex();
            int colIndex = current.getLocation().colIndex();
        
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    // only up, down, left, right neighbors
                    if (!checkBoundsAndDirection(i, j, rowIndex, colIndex)) {
                        continue;
                    }

                    KSCell neighbor = matrix[rowIndex + i][colIndex + j];
                    if (neighbor.getGroup() != current.getGroup()) {
                        neighborColors.add(neighbor.getColor());
                    }
                }
            }
        }

        // get colors that neighboring cells do not share and use the first such color
        List<Color> colors = new ArrayList<>(COLORS);
        Collections.shuffle(colors);
        for (Color c : colors) {
            if (!neighborColors.contains(c)) {
                color = c;
                break;
            }
        }

        for (KSCell cell: cage) {
            cell.setColor(color);
        }
    }

    /**
     * Checks whether the row, column, nonet, and cage of {@code cell} do not
     * violate any Killer Sudoku rules.
     * 
     * @pre {@code matrix != null && cageMap != null}
     * @post {@code matrix == \old(matrix) && cageMap == \old(cageMap)}
     * @param cell the cell whose row, column, nonet, and cage are checked
     * @return {@code true} if no rules are violated and {@code false} otherwise
     */
    public boolean isValidPuzzleInstance(final KSCell cell) {
        return checkCage(cell) && checkRow(cell) && checkColumn(cell) && checkNonet(cell);
    }
    
    /**
     * Checks whether this grid is full (no more empty cells).
     *
     * @return whether this grid is full
     */
    public boolean isFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (matrix[i][j].getValue() == 0) { // or other condition for being "empty"
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Clears the non-blocked cells.
     *
     * @post {@code \forall Cell cell : cells;
     *   (cell.isEmpty()) == ! cell.isBlocked)}
     */
    public void clear() {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                matrix[i][j].setState(0);
            }
        }     
    }

    /**
     * Each cage has a sum and a unique cage number, which need to be extracted.
     * 
     * @param scanner scanner containing the puzzle input
     * @param groups a list of cage numbers (groups)
     * @param cageSums a list of every cage's sum
     */
    private void readGroupsAndCageSums(Scanner scanner, List<Integer> groups,
        List<Integer> cageSums) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // first 9 lines contain data for cage groups
                // reading cage groups
                String numberGroupString = scanner.next();
                int group = Integer.valueOf(numberGroupString);

                if (group <= 0 || group > 81) {
                    throw new IllegalArgumentException("invalid group number " + group);
                }

                groups.add(group);
            }
        }

        // reading cage sums
        while (scanner.hasNext()) {
            int cageSum = scanner.nextInt();
            cageSums.add(cageSum);
        }
    }

    /**
     * Creating cells, each one of which belongs to a cage with a sum and a unique number.
     * 
     * @param groups list with all cage numbers (groups)
     */
    private void createCells(List<Integer> groups) {
        for (int i = 0; i < ROWS; i++) {
            KSCell[] row = new KSCell[COLS];

            for (int j = 0; j < COLS; j++) {
                int group = groups.get(i * ROWS + j);
                KSCell cell = new KSCell(group, i, j);
                List<KSCell> cage = cageMap.get(group);

                if (cage == null) {
                    cage = new ArrayList<>();
                    cage.add(cell);
                    cageMap.put(group, cage);
                } else {
                    if (cage.size() == 9) {
                        throw new IllegalArgumentException("invalid puzzle instance - "
                            + "more than 9 numbers in a cage");
                    }

                    cage.add(cell);
                }

                row[j] = cell;
            }

            matrix[i] = row;
        }
    }

    /**
     * Determine each cell's color according to its neighbors, and set the cell's cage sum.
     * 
     * @param cageSums list with all cages' sums
     */
    private void setColorsAndCageSums(List<Integer> cageSums) {
        // set cage sum and colors
        int index = 0;
        for (int group : cageMap.keySet()) {
            List<KSCell> cage = cageMap.get(group);
            int cageSum = cageSums.get(index++);

            cage.get(0).setCageSum(cageSum);
            determineColor(cage);
        }
    }

    /**
     * Checks whether the cage of {@code cell} has unique entries and valid cage sum.
     * 
     * @param cell the cell's cage to be checked
     * @return {@code true} if there are only distinct entries, and valid cage sum,
     * {@code false} otherwise 
     */
    private boolean checkCage(final KSCell cell) {
        List<KSCell> cage = cageMap.get(cell.getGroup());
        int cageSumActual = cage.get(0).getCageSum();
        int cageSum = cell.getValue();
        boolean allFilled = true; // if all cells in the cage are filled

        for (KSCell c : cage) {
            // skip self
            if (c.getLocation().equals(cell.getLocation())) {
                continue;
            }

            if (c.getValue() == cell.getValue()) {
                return false;
            }

            if (c.getValue() == 0) {
                allFilled = false;
            }

            cageSum += c.getValue();
        }

        if (allFilled) {
            return (cageSum == cageSumActual);
        }

        return true;
    }

    /**
     * Checks whether a row of {@code cell} has unique entries.
     * 
     * @param cell the cell whose row is to be checked
     * @return {@code true} if all values are distinct, {@code false} otherwise
     */
    private boolean checkRow(final KSCell cell) {
        int rowIndex = cell.getLocation().rowIndex();
        int colIndex = cell.getLocation().colIndex();

        for (int j = 0; j < COLS; j++) {
            // skip self
            if (j == colIndex) {
                continue;
            }

            if (matrix[rowIndex][j].getValue() == cell.getValue()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether a column of {@code cell} has unique entries.
     * 
     * @param cell the cell whose column is to be checked
     * @return {@code true} if all values are distinct, {@code false} otherwise
     */
    private boolean checkColumn(final KSCell cell) {
        int rowIndex = cell.getLocation().rowIndex();
        int colIndex = cell.getLocation().colIndex();

        for (int i = 0; i < ROWS; i++) {
            // skip self
            if (i == rowIndex) {
                continue;
            }

            if (matrix[i][colIndex].getValue() == cell.getValue()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether the nonet of {@code cell} has unique entries.
     * 
     * @param cell the cell whose nonet is to be checked
     * @return {@code true} if all values are distinct, {@code false} otherwise 
     */
    private boolean checkNonet(final KSCell cell) {
        int rowIndex = cell.getLocation().rowIndex();
        int colIndex = cell.getLocation().colIndex();
        int startRowIndex = ((int) Math.floor(rowIndex / 3)) * 3;
        int startColIndex = ((int) Math.floor(colIndex / 3)) * 3;

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                // skip self
                if (startRowIndex + i == rowIndex && startColIndex + j == colIndex) {
                    continue;
                }

                KSCell c = matrix[startRowIndex + i][startColIndex + j];
                if (c.getValue() == cell.getValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks for both direction and bounds.
     * 
     * @param currentRowIndex current row index in the iteration
     * @param currentColIndex current col index in the iteration
     * @param cellRowIndex cell row index
     * @param cellColIndex cell col index
     * @return {@code true} if both requirements are met, {@code false} otherwise
     */
    private boolean checkBoundsAndDirection(int currentRowIndex, int currentColIndex,
        int cellRowIndex, int cellColIndex) {
        return checkDirection(currentRowIndex, currentColIndex)
            && checkBounds(currentRowIndex, currentColIndex, cellRowIndex, cellColIndex);
    }

    /**
     * Only up, down, right, and left are allowed.
     * 
     * @param currentRowIndex current row index in the iteration
     * @param currentColIndex current col index in the iteration
     * @return {@code true} if the direction is legal, {@code false} otherwise
     */
    private boolean checkDirection(int currentRowIndex, int currentColIndex) {
        return (currentRowIndex == 0 && currentColIndex != 0)
            || (currentRowIndex != 0 && currentColIndex == 0);
    }

    /**
     * Checks whether neighboring indices do not leave the grid.
     * 
     * @param currentRowIndex current row index in the iteration
     * @param currentColIndex current col index in the iteration
     * @param cellRowIndex cell row index
     * @param cellColIndex cell col index
     * @return {@code true} if indices are legal, {@code false} otherwise 
     */
    private boolean checkBounds(int currentRowIndex, int currentColIndex,
        int cellRowIndex, int cellColIndex) {
        return !(cellRowIndex + currentRowIndex < 0 
            || cellRowIndex + currentRowIndex >= ROWS
            || cellColIndex + currentColIndex < 0
            || cellColIndex + currentColIndex >= COLS);
    }

    @Override
    public String toString() {
        String res = "";

        for (int i = 0; i < ROWS; i++) {
            String row = "";

            for (int j = 0; j < COLS; j++) {
                String spaceOrNot = (j < COLS - 1) ? " " : "";
                row += matrix[i][j].getGroup() + spaceOrNot;
            }

            res += row + "\n";
        }

        for (int group : cageMap.keySet()) {
            List<KSCell> cage = cageMap.get(group);
            res += cage.get(0).getCageSum() + " ";
        }

        // removing last white space
        return res.substring(0, res.length() - 1);
    }

    /**
     * Returns puzzle's grid.
     * 
     * @return {@code matrix}
     */
    public KSCell[][] getMatrix() {
        return this.matrix;
    }
    
    /**
     * Returns cell's value.
     * @param rowIndex index of row
     * @param columnIndex index of column
     * @return {@code matrix}
     */
    public KSCell getCell(final int rowIndex, final int columnIndex) {
        return matrix[rowIndex][columnIndex];
    }
}