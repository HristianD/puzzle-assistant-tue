package ypa.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ypa.model.KSPuzzle.Mode;

/**
 * Test cases for {@code KSPuzzle}.
 * 
 * @author 20224323
 */
public class KSPuzzleTest {
    
    private KSPuzzle ksPuzzle;
    
    /**
     * Load in the example killer sudoku.
     */
    @BeforeEach
    void setUp() {
        try {
            // Assuming that the puzzle1.zgr file is in the "puzzles" folder
            Scanner scanner = new Scanner(new File("puzzles/puzzle1.zgr"));
            ksPuzzle = new KSPuzzle(scanner, "TestPuzzle");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test the constructor of class KSPuzzle.
     */
    @Test
    public void testConstructor() {
        System.out.println("KSPuzzle constructor");
        Mode defaultMode = Mode.VIEW;
        
        // Check that mode and name are initialized correctly.
        assertEquals(defaultMode, ksPuzzle.getMode());
        assertEquals("TestPuzzle", ksPuzzle.getName());

        // Check that the puzzle matrix is initialized correctly
        KSCell[][] matrix = ksPuzzle.getMatrix();
        assertNotNull(matrix);
        assertEquals(9, matrix.length);
        assertEquals(9, matrix[0].length);
    }
    
    /**
     * Test of getMatrix method, of class KSPuzzle.
     */
    @Test
    public void testGetMatrix() {
        System.out.println("KSPuzzle getMatrix");
        
        // Check that the puzzle matrix is initialized correctly
        KSCell[][] matrix = ksPuzzle.getMatrix();
        assertNotNull(matrix);
        assertEquals(9, matrix.length);
        assertEquals(9, matrix[0].length);
    }

    /**
     * Test of isValid method, of class KSPuzzle.
     */
    @Test
    public void testIsValid() {
        System.out.println("KSPuzzle isValid");
        KSCell testCell = ksPuzzle.getCell(0, 0);
        testCell.setState(1);
        assertTrue(ksPuzzle.isValid(testCell));
    }

    /**
     * Test of isSolved method, of class KSPuzzle.
     */
    @Test
    public void testIsSolved() {
        System.out.println("KSPuzzle isSolved");
        ksPuzzle.getCell(0, 0).setState(1);
        assertFalse(ksPuzzle.isSolved(ksPuzzle.getCell(0, 0)));
    }

    /**
     * Test of clear method, of class KSPuzzle.
     */
    @Test
    public void testClear() {
        System.out.println("KSPuzzle clear");
        ksPuzzle.getCell(0, 0).setState(1);
        ksPuzzle.getCell(0, 1).setState(2);

        ksPuzzle.clear();

        // Check that all cells are empty after clearing
        for (int i = 0; i < ksPuzzle.getRowCount(); i++) {
            for (int j = 0; j < ksPuzzle.getColumnCount(); j++) {
                assertEquals(0, ksPuzzle.getCell(i, j).getValue());
            }
        }
    }

    /**
     * Test of getName method, of class KSPuzzle.
     */
    @Test
    public void testGetName() {
        System.out.println("KSPuzzle getName");
        assertEquals("TestPuzzle", ksPuzzle.getName());
    }

    /**
     * Test of setName method, of class KSPuzzle.
     */
    @Test
    public void testSetName() {
        System.out.println("KSPuzzle setName, edit mode");
        // Set the puzzle to EDIT mode
        ksPuzzle.setMode(KSPuzzle.Mode.EDIT);
        ksPuzzle.setName("NewName");
        assertEquals("NewName", ksPuzzle.getName());
    }
    
    /**
     * Test of setName method, of class KSPuzzle.
     */
    @Test
    public void testSetNameException() {
        System.out.println("KSPuzzle setName, non-edit mode (exception)");
        // Set the puzzle to VIEW mode
        ksPuzzle.setMode(KSPuzzle.Mode.VIEW);
        
        Throwable e = assertThrows(IllegalStateException.class, () -> ksPuzzle.setName("NewName"));

        assertNotNull(e.getMessage(), "Message should not be null");
    }

    /**
     * Test of getMode method, of class KSPuzzle.
     */
    @Test
    public void testGetMode() {
        System.out.println("KSPuzzle getMode");
        Mode defaultMode = Mode.VIEW;
        assertEquals(defaultMode, ksPuzzle.getMode());
    }

    /**
     * Test of setMode method, of class KSPuzzle.
     */
    @Test
    public void testSetMode() {
        System.out.println("KSPuzzlesetMode");
        // First show default mode to show it actually changes after setMode.
        Mode defaultMode = Mode.VIEW;
        assertEquals(defaultMode, ksPuzzle.getMode());
        
        ksPuzzle.setMode(Mode.SOLVE);
        assertEquals(Mode.SOLVE, ksPuzzle.getMode());
    }

    /**
     * Test of getMinNumber method, of class KSPuzzle.
     */
    @Test
    public void testGetMinNumber() {
        System.out.println("KSPuzzle getMinNumber");
        assertEquals(1, ksPuzzle.getMinNumber());
    }

    /**
     * Test of getMaxNumber method, of class KSPuzzle.
     */
    @Test
    public void testGetMaxNumber() {
        System.out.println("KSPuzzle getMaxNumber");
        assertEquals(9, ksPuzzle.getMaxNumber());
    }

    /**
     * Test of toString method, of class KSPuzzle.
     */
    @Test
    public void testToString() {
        System.out.println("KSPuzzle toString");
        String expectedString = "1 1 2 2 2 3 4 5 6\n" 
                + "7 7 8 8 3 3 4 5 6\n" 
                + "7 7 9 9 3 10 11 11 6\n" 
                + "12 13 13 9 14 10 11 15 6\n" 
                + "12 16 16 17 14 10 15 15 18\n" 
                + "19 16 20 17 14 21 22 22 18\n" 
                + "19 20 20 17 23 21 21 24 24\n" 
                + "19 25 26 23 23 27 27 24 24\n" 
                + "19 25 26 23 28 28 28 29 29\n" 
                + "3 15 22 4 16 15 25 17 9 8 20 6 14 17 17 13 20 12 27 6 20 6 10 14 8 16 15 13 17";

        assertEquals(expectedString, ksPuzzle.toString());
    }

    /**
     * Test of getColumnCount method, of class KSPuzzle.
     */
    @Test
    public void testGetColumnCount() {
        System.out.println("KSPuzzle getColumnCount");
        assertEquals(9, ksPuzzle.getColumnCount());
    }

    /**
     * Test of getRowCount method, of class KSPuzzle.
     */
    @Test
    public void testGetRowCount() {
        System.out.println("KSPuzzle getRowCount");
        assertEquals(9, ksPuzzle.getRowCount());
    }

    /**
     * Test of has method, of class KSPuzzle.
     */
    @Test
    public void testHas() {
        assertTrue(ksPuzzle.has(0, 0)); // Valid indices
        assertTrue(ksPuzzle.has(4, 8)); // Valid indices
        assertFalse(ksPuzzle.has(-1, 0)); // Invalid row index
        assertFalse(ksPuzzle.has(0, 10)); // Invalid column index
        assertFalse(ksPuzzle.has(10, 10)); // Both indices out of bounds
    }
    
}
