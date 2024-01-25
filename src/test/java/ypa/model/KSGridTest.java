package ypa.model;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code KSGrid}.
 * 
 * @author 20224323
 */
public class KSGridTest {
    
    private KSGrid ksGrid;
    
    /**
     * Load in the example killer sudoku.
     */
    @BeforeEach
    void setUp() {
        try {
            // Assuming that the puzzle1.zgr file is in the "puzzles" folder
            Scanner scanner = new Scanner(new File("puzzles/puzzle1.zgr"));
            ksGrid = new KSGrid(scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test the constructor.
     */
    @Test
    public void testConstructorWithValidScanner() {
        System.out.println("KSGrid constructor, valid scanner");
        // Replace pathname with the actual path to your puzzle file
        File file = new File("puzzles/puzzle1.zgr");

        try {
            Scanner scanner = new Scanner(file);
            KSGrid ksGrid = new KSGrid(scanner);
            assertNotNull(ksGrid);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Failed to read puzzle file, edit the path to a file "
                    + "containing a killer sudoku puzzle.");
        }
    }
    
    /**
     * Test the constructor when the scanner is null.
     */
    @Test
    public void testConstructorNullPointerException() {
        System.out.println("KSGrid constructor, null scanner");
        Scanner nullScanner = null;

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new KSGrid(nullScanner)
        );

        assertEquals("KSGrid.pre failed: scanner == null", exception.getMessage());
    }

    /**
     * Test of determineColor method, of class KSGrid.
     */
    @Test
    public void testDetermineColor() {
        System.out.println("KSGrid determineColor");
        // Create a mock cage with cells
        List<KSCell> mockCage = createMockCage();

        // Manually set colors for neighboring cells
        mockCage.get(1).setColor(new Color(255, 255, 158)); // Light yellow
        mockCage.get(3).setColor(new Color(144, 238, 144)); // Light green

        // Call the determineColor method for the remaining cells in the cage
        ksGrid.determineColor(mockCage);

        assertNotNull(mockCage.get(0).getColor());
        assertNotNull(mockCage.get(2).getColor());
        assertNotNull(mockCage.get(4).getColor());
        assertNotNull(mockCage.get(5).getColor());
    }

    // Helper method to create a mock cage with cells
    private List<KSCell> createMockCage() {
        List<KSCell> mockCage = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            mockCage.add(new KSCell(1, i, 0)); // Assuming a cage with group 1
        }

        return mockCage;
    }
    

    /**
     * Test of isValidPuzzleInstance method, of class KSGrid.
     */
    @Test
    public void testIsValidPuzzleInstance() {
        System.out.println("KSGrid isValidPuzzleInstance, valid instance");
        // Get a cell from the loaded puzzle
        KSCell validCell = ksGrid.getCell(0, 0);

        validCell.setState(1);

        assertTrue(ksGrid.isValidPuzzleInstance(validCell)); 
    }
    
    /**
     * Test of isValidPuzzleInstance method, of class KSGrid.
     */
    @Test
    public void testIsInvalidPuzzleInstance() {
        System.out.println("KSGrid isValidPuzzleInstance, invalid instance");
        // Set two cells in the same column to the same value
        KSCell invalidCell = ksGrid.getCell(0, 0);
        invalidCell.setState(1);
        KSCell invalidCell2 = ksGrid.getCell(1, 0);
        invalidCell2.setState(1);

        assertFalse(ksGrid.isValidPuzzleInstance(invalidCell)); 
    }

    /**
     * Test of isFull method, of class KSGrid.
     */
    @Test
    public void testIsFull() {
        System.out.println("KSGrid isFull");
        assertFalse(ksGrid.isFull());
    }

    /**
     * Test of clear method, of class KSGrid.
     */
    @Test
    public void testClear() {
        System.out.println("KSGrid clear");
        // Clear the grid
        ksGrid.clear();

        // Ensure all cells are in the empty state
        for (int i = 0; i < KSGrid.ROWS; i++) {
            for (int j = 0; j < KSGrid.COLS; j++) {
                assertEquals(0, ksGrid.getMatrix()[i][j].getValue());
            }
        }

    }

    /**
     * Test of toString method, of class KSGrid.
     */
    @Test
    public void testToString() {
        System.out.println("KSGrid toString");
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

        assertEquals(expectedString, ksGrid.toString());
    }

    /**
     * Test of getMatrix method, of class KSGrid.
     */
    @Test
    public void testGetMatrix() {
        System.out.println("KSGrid getMatrix");
        KSCell[][] matrix = ksGrid.getMatrix();

        assertNotNull(matrix);
        assertEquals(KSGrid.ROWS, matrix.length);
        assertEquals(KSGrid.COLS, matrix[0].length);

        for (int i = 0; i < KSGrid.ROWS; i++) {
            for (int j = 0; j < KSGrid.COLS; j++) {
                assertNotNull(matrix[i][j]);
                assertEquals(i, matrix[i][j].getLocation().rowIndex());
                assertEquals(j, matrix[i][j].getLocation().colIndex());
            }
        }
    }

    /**
     * Test of getCell method, of class KSGrid.
     */
    @Test
    public void testGetCell() {
        System.out.println("KSGrid getCell");
        KSCell cell = ksGrid.getCell(0, 0);
        assertFalse(ksGrid.isValidPuzzleInstance(cell));
    }
    
}
