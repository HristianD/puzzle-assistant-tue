package ypa.reasoning;

import java.util.Arrays;
import ypa.command.Command;
import ypa.command.CompoundCommand;
import ypa.command.SetCommand;
import ypa.model.KSPuzzle;
import ypa.model.KSCell;
import ypa.model.KSGrid;

/**
 * A class that applies the rule of 45.
 * Each sudoku region (i.e., row, column, or nonet) contains the digits one through nine.
 * Thus, each sudoku region has a total value of 45. If S is the sum of all the cages
 * contained entirely in a region, then the cells not covered must sum to 45-S.
 */
public class Rule45Reasoner extends Reasoner {
    /** The grid of the puzzle. */
    private final KSCell[][] matrix;

    /**
     * Create a class that applies the above stated strategy.
     * 
     * @param puzzle the puzzle the strategy is applied to
     */
    public Rule45Reasoner(KSPuzzle puzzle) {
        super(puzzle);
        this.matrix = puzzle.getMatrix();
    }
    
    @Override
    public CompoundCommand apply() {
        CompoundCommand command = super.apply();
        if (applyToCells(command) == null) {
            return null;
        }

        return command;
    }

    private CompoundCommand applyToCells(CompoundCommand result) {
        int oldSize = result.size();
        if (checkRows(result) != null) {
            if (oldSize != result.size()) {
                return result;
            }

            if (checkCols(result) != null) {
                if (oldSize != result.size()) {
                    return result;
                }

                if (checkNonets(result) != null) {
                    return result;
                }

                return null;
            }

            return null;
        }
        
        return null;
    }

    private CompoundCommand checkRows(final CompoundCommand result) {
        // check rows
        for (int row = 0; row < KSGrid.ROWS; row++) {
            // sum values in a row of cells
            int sum = Arrays.stream(matrix[row]).mapToInt(KSCell::getValue).sum();
            int missingSum = 45 - sum;
            if (missingSum > 9) {
                continue;
            }

            // find count of cells with value 0
            int missingCount = (int) Arrays.stream(matrix[row])
                .filter(cell -> cell.getValue() == 0).count();

            if (missingCount == 1) {
                // Only one cell is empty, fill it with the missing value
                for (int col = 0; col < KSGrid.COLS; col++) {
                    KSCell cell = matrix[row][col];

                    if (cell.getValue() == 0) {
                        return setCellState(result, cell, missingSum);
                    }
                }
            }
        }

        return result;
    }

    private CompoundCommand checkCols(final CompoundCommand result) {
        // check cols
        for (int col = 0; col < KSGrid.COLS; col++) {
            int sum = 0;

            for (int row = 0; row < KSGrid.ROWS; row++) {
                sum += matrix[row][col].getValue();
            }

            int missingSum = 45 - sum;
            if (missingSum > 9) {
                continue;
            }

            final int finalCol = col;
            int missingCount = (int) Arrays.stream(matrix)
                .filter(row -> row[finalCol].getValue() == 0).count();

            if (missingCount == 1) {
                // Only one cell is empty, fill it with the missing value
                for (int row = 0; row < 9; row++) {
                    KSCell cell = matrix[row][col];

                    if (cell.getValue() == 0) {
                        return setCellState(result, cell, missingSum);
                    }
                }
            }
        }

        return result;
    }

    private CompoundCommand checkNonets(final CompoundCommand result) {
        // check nonets
        for (int startRow = 0; startRow < KSGrid.ROWS; startRow += 3) {
            for (int startCol = 0; startCol < KSGrid.COLS; startCol += 3) {
                int sum = getNonetSum(startRow, startCol);
                int missingSum = 45 - sum;
                int missingCount = 0;
                int missingRow = -1;
                int missingCol = -1;

                if (missingSum > 9) {
                    continue;
                }

                findMissingRowAndCol(startRow, startCol, missingCount, missingRow, missingCol);
                if (missingCount == 1) {
                    // Only one cell is empty, fill it with the missing value
                    KSCell cell = matrix[missingRow][missingCol];
                    
                    if (cell.getValue() == 0) {
                        return setCellState(result, cell, missingSum);
                    }
                }
            }
        }

        return result;
    }

    private int getNonetSum(int startRow, int startCol) {
        int sum = 0;

        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                sum += matrix[row][col].getValue();
            }
        }

        return sum;
    }

    private void findMissingRowAndCol(int startRow, int startCol, int missingCount,
        int missingRow, int missingCol) {
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (matrix[row][col].getValue() == 0) {
                    missingCount++;
                    missingRow = row;
                    missingCol = col;
                }
            }
        }
    }

    private CompoundCommand setCellState(final CompoundCommand result, KSCell cell, int state) {
        final Command command = new SetCommand(cell, state);
        command.execute();
        final boolean valid = puzzle.isValid(cell);
        command.revert();

        if (valid) {
            result.add(command);
        
            return result;
        }

        return null;
    }
}