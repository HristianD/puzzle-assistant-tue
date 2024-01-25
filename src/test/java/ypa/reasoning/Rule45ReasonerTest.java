package ypa.reasoning;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ypa.command.CompoundCommand;
import ypa.model.KSCell;
import ypa.model.KSPuzzle;
import ypa.solvers.BacktrackSolver;

/**
 * Test cases for {@code Rule45Reasoner}.
 * 
 * @author 20224323
 */
public class Rule45ReasonerTest {
    
    private KSPuzzle puzzle;
    
    /**
     * Load in the example killer sudoku for testing.
     */
    @BeforeEach
    void setUp() {
        try {
            // Assuming that the puzzle1.zgr file is in the "puzzles" folder
            Scanner scanner = new Scanner(new File("puzzles/puzzle1.zgr"));
            puzzle = new KSPuzzle(scanner, "TestPuzzle");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test Rule45Reasoner.
     */
    @Test
    public void testRule45Reasoner() {
        Rule45Reasoner reasoner = new Rule45Reasoner(puzzle);
        BacktrackSolver solver = new BacktrackSolver(puzzle, null);

        // Apply the rule and attempt to solve the puzzle
        boolean result = solver.solve();

        // Check if the puzzle is solved after applying the rule
        assertTrue(result, "Puzzle should be solvable");
        
        for (int i = 0; i < puzzle.getRowCount(); i++) {
            for (int j = 0; j < puzzle.getColumnCount(); j++) {
                KSCell cell = puzzle.getCell(i, j);
                assertTrue(puzzle.isValid(cell));
            }
        }
    }
    
}
