package ypa.reasoning;

import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

import ypa.command.CompoundCommand;
import ypa.model.KPuzzle;
import ypa.reasoning.Reasoner;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import ypa.model.KSPuzzle;

/**
 * Test cases for {@link Reasoner}.
 *
 * @author wstomv
 */
public class ReasonerTest {

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
     * Test the constructor with puzzle == null.
     */
    @Test
    public void testConstructorWithNullPuzzle() {
        System.out.println("Reasoner constructor, puzzle == null");
        Throwable e = assertThrows(IllegalArgumentException.class, () -> {
            new Reasoner(null);
        });
        assertNotNull(e.getMessage(), "Message should not be null");
    }

    /**
     * Test of apply method, of class Reasoner.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        Reasoner instance = new Reasoner(puzzle);
        CompoundCommand result = instance.apply();
        assertAll(
                () -> assertEquals(0, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed")
        );
    }

}
