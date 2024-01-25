package ypa.solvers;

import java.io.File;
import java.io.FileNotFoundException;
import ypa.model.KPuzzle;
//import ypa.reasoning.EntryWithOneEmptyCell;
//import ypa.reasoning.FixpointReasoner;
import ypa.reasoning.Reasoner;
import ypa.reasoning.ReasonerTest;
import ypa.solvers.BacktrackSolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import ypa.model.KSCell;
import ypa.model.KSPuzzle;

/**
 * Test cases for {@link BacktrackSolver}.
 *
 * @author wstomv
 */
public class BacktrackSolverTest {

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
     * Test of constructor of BacktrackSolver.
     */
    @Test
    public void testConstructorBacktrackSolver() {
        System.out.println("BacktrackSolver constructor");
        Throwable e = assertThrows(IllegalArgumentException.class, () -> {
            new BacktrackSolver(null, null);
        });
        assertNotNull(e.getMessage(), "Message should not be null");
    }
    
    /**
     * Test of solve method, of class BacktrackSolver.
     */
    @Test
    public void testSolveWithoutReasoner() {
        System.out.println("solve w/o reasoner");
        BacktrackSolver instance = new BacktrackSolver(puzzle, null);
        boolean expResult = true;
        System.out.println(puzzle.toString());
        boolean result = instance.solve();
        System.out.println(puzzle.toString());
        KSCell cell = puzzle.getCell(0, 0);
        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertTrue(puzzle.isValid(cell), "puzzle should be valid"),
                () -> assertTrue(puzzle.isSolved(cell), "puzzle solved")
        );
    }

//    /**
//     * Test of solve method, of class BacktrackSolver.
//     */
//    @Test
//    public void testSolveWithSimpleReasoner() {
//        System.out.println("solve with simple reasoner");
//        Reasoner reasoner = new EntryWithOneEmptyCell(puzzle);
//        BacktrackSolver instance = new BacktrackSolver(puzzle, reasoner);
//        boolean expResult = true;
//        System.out.println(puzzle.toString());
//        boolean result = instance.solve();
//        System.out.println(puzzle.toString());
//        KSCell cell = puzzle.getCell(0, 0);
//        assertAll(
//                () -> assertEquals(expResult, result, "return value"),
//                () -> assertTrue(puzzle.isValid(null), "puzzle should be valid"),
//                () -> assertTrue(puzzle.isSolved(cell), "puzzle solved")
//        );
//    }
//
//    /**
//     * Test of solve method, of class BacktrackSolver.
//     */
//    @Test
//    public void testSolveWithFixpointReasoner() {
//        System.out.println("solve with fixpoint");
//        Reasoner reasoner = new EntryWithOneEmptyCell(puzzle);
//        reasoner = new FixpointReasoner(puzzle, reasoner);
//        BacktrackSolver instance = new BacktrackSolver(puzzle, reasoner);
//        boolean expResult = true;
//        System.out.println(puzzle.toString());
//        boolean result = instance.solve();
//        System.out.println(puzzle.toString());
//        KSCell cell = puzzle.getCell(0, 0);
//        assertAll(
//                () -> assertEquals(expResult, result, "return value"),
//                () -> assertTrue(puzzle.isValid(null), "puzzle should be valid"),
//                () -> assertTrue(puzzle.isSolved(cell), "puzzle solved")
//        );
//    }

}
