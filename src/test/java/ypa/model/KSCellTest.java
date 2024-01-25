package ypa.model;

import java.awt.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing for the KSCell Class.
 * @author 20224323
 */
public class KSCellTest {
    
    /**
     * Test constructor of KSCell.
     */
    @Test
    public void testKSCell() {
        System.out.println("KSCell constructor");
        
        int groupNumber = 1;
        int rowIndex = 2;
        int colIndex = 3;

        KSCell instance = new KSCell(groupNumber, rowIndex, colIndex);

        assertEquals(groupNumber, instance.getGroup());
        assertEquals(rowIndex, instance.getLocation().rowIndex());
        assertEquals(colIndex, instance.getLocation().colIndex());
        assertEquals(0, instance.getState()); 
        assertEquals(0, instance.getCageSum()); 
        assertEquals(null, instance.getColor());
    }

    /**
     * Test of getState method, of class KSCell.
     */
    @Test
    public void testGetState() {
        System.out.println("KSCell getState");
        int newState = 3;
        KSCell instance = new KSCell(1, 0, 0);
        instance.setState(newState);

        assertEquals(newState, instance.getState());
    }

    /**
     * Test of setState method, of class KSCell.
     */
    @Test
    public void testSetState() {
        System.out.println("KSCell setState");
        KSCell instance = new KSCell(1, 0, 0);
        
        // Assert the default to show state changes after setState
        assertEquals(0, instance.getState());
        
        int newState = 5;
        instance.setState(newState);

        assertEquals(newState, instance.getState());
    }

    /**
     * Test of setCageSum method, of class KSCell.
     */
    @Test
    public void testSetCageSum() {
        System.out.println("KSCell setCageSum");
        KSCell instance = new KSCell(1, 0, 0);
        
        // Assert the default to show cageSum changes after setState
        assertEquals(0, instance.getCageSum());
        
        int newCageSum = 5;
        instance.setCageSum(newCageSum);

        assertEquals(newCageSum, instance.getCageSum());
    }

    /**
     * Test of getCageSum method, of class KSCell.
     */
    @Test
    public void testGetCageSum() {
        System.out.println("KSCell getCageSum");
        int newCageSum = 3;
        KSCell instance = new KSCell(1, 0, 0);
        instance.setCageSum(newCageSum);

        assertEquals(newCageSum, instance.getCageSum());
    }

    /**
     * Test of getValue method, of class KSCell.
     */
    @Test
    public void testGetValue() {
        System.out.println("KSCell getValue");
        int defaultValue = 0;
        KSCell instance = new KSCell(1, 0, 0);
        assertEquals(defaultValue, instance.getValue());
    }

    /**
     * Test of getLocation method, of class KSCell.
     */
    @Test
    public void testGetLocation() {
        System.out.println("KSCell getLocation");
        KSCell.Location expectedLocation = new KSCell.Location(1, 2);
        KSCell instance = new KSCell(1, 1, 2);
        KSCell.Location result = instance.getLocation();
        assertEquals(expectedLocation, result, "Location should match");
    }

    /**
     * Test of getLocationString method, of class KSCell.
     */
    @Test
    public void testGetLocationString() {
        System.out.println("KSCell getLocationString");
        KSCell instance = new KSCell(1, 1, 2);
        String expectedResult = "Selected cell 1, 2";
        String result = instance.getLocationString();
        assertEquals(expectedResult, result, "Location string should match");
    }

    /**
     * Test of getGroup method, of class KSCell.
     */
    @Test
    public void testGetGroup() {
        System.out.println("KSCell getGroup");
        KSCell instance = new KSCell(1, 0, 0);
        assertEquals(1, instance.getGroup());
    }

    /**
     * Test of setColor method, of class KSCell.
     */
    @Test
    public void testSetColor() {
        System.out.println("KSCell setColor");
        KSCell instance = new KSCell(1, 0, 0);
        
        // Assert the default to show color changes after setState
        assertEquals(null, instance.getColor());
        
        Color newColor = Color.BLUE;
        instance.setColor(newColor);

        assertEquals(newColor, instance.getColor());
    }

    /**
     * Test of getColor method, of class KSCell.
     */
    @Test
    public void testGetColor() {
        System.out.println("KSCell getColor");
        KSCell instance = new KSCell(1, 1, 2);
        assertEquals(null, instance.getColor());
    }

    /**
     * Test of isEmpty method, of class KSCell.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("KSCell isEmpty");
        KSCell cellWithValue = new KSCell(1, 1, 2);
        KSCell cellEmpty = new KSCell(2, 3, 4);
        cellWithValue.setState(2);

        assertFalse(cellWithValue.isEmpty(cellWithValue), "Cell with value should not be empty");
        assertTrue(cellWithValue.isEmpty(cellEmpty), "Cell without value should be empty");
    }

    /**
     * Test of toString method, of class KSCell.
     */
    @Test
    public void testToString() {
        System.out.println("KSCell toString");
        KSCell cellWithValue = new KSCell(1, 1, 2);
        KSCell cellEmpty = new KSCell(2, 3, 4);

        cellWithValue.setState(5);

        assertEquals("5", cellWithValue.toString(), "String representation should match the value");
        assertEquals("", cellEmpty.toString(), 
                "Empty cell should have an empty string representation");
    }
    
}
