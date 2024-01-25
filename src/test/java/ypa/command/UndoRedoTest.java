package ypa.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for class UndoRedo.
 * 
 * @author 20224323
 */
public class UndoRedoTest {
    
    /**
     * The subject under test.
     */
    private UndoRedo instance;

    /**
     * A command for testing.
     */
    private Command command;

    /**
     * Initializes the subject under test.
     */
    @BeforeEach
    public void setUp() {
        instance = new UndoRedo();
        command = new TestCommand();
    }

    /**
     * Test of canUndo method, of class UndoRedo.
     */
    @Test
    public void testCanUndo() {
        System.out.println("canUndo");
        boolean result = instance.canUndo();
        assertFalse(result, "Result false when empty");

        command.execute();
        instance.did(command);
        result = instance.canUndo();
        assertTrue(result, "Result when one command done");
    }

    /**
     * Test of canRedo method, of class UndoRedo.
     */
    @Test
    public void testCanRedo() {
        System.out.println("canRedo");
        boolean result = instance.canRedo();
        assertFalse(result, "Result false when empty");

        command.execute();
        instance.did(command);
        instance.undo(true);
        result = instance.canRedo();
        assertTrue(result, "Result when one command undone");
    }

    /**
     * Test of lastDone method, of class UndoRedo.
     */
    @Test
    public void testLastDone() {
        System.out.println("lastDone");
        instance.did(command);
        Command result = instance.lastDone();
        assertEquals(command, result, "Result");
    }

    /**
     * Test of lastDone method, of class UndoRedo.
     */
    @Test
    public void testLastUndone() {
        System.out.println("lastUndone");
        instance.did(command);
        instance.undo(true);
        Command result = instance.lastUndone();
        assertEquals(command, result, "Result");
    }

    /**
     * Test of lastDone method for exceptions, of class UndoRedo.
     */
    @Test
    public void testLastDoneExceptions() {
        System.out.println("lastDone exceptions");
        Throwable e = assertThrows(IllegalStateException.class, instance::lastDone);
        assertNotNull(e.getMessage(), "message should not be null");
    }

    /**
     * Test of lastDone method for exceptions, of class UndoRedo.
     */
    @Test
    public void testLastUndoneExceptions() {
        System.out.println("lastUndone exceptions");
        Throwable e = assertThrows(IllegalStateException.class, instance::lastUndone);
        assertNotNull(e.getMessage(), "message should not be null");
    }

    /**
     * Test of clear method, of class UndoRedo.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        for (int i = 0; i != 4; ++i) {
            final Command command = new TestCommand();
            instance.did(command);
        }
        for (int i = 0; i != 2; ++i) {
            instance.undo(true);
        }
        // both stacks are not empty (contain two commands)
        instance.clear();
        assertAll(
                () -> assertFalse(instance.canUndo(), "not canUndo()"),
                () -> assertFalse(instance.canRedo(), "not canRedo()")
        );
    }

    /**
     * Test of did method, of class UndoRedo.
     */
    @Test
    public void testDidExecuted() {
        System.out.println("did, command already executed");
        command.execute();
        instance.did(command);
        assertAll(
                () -> assertTrue(instance.canUndo(), "canUndo()"),
                () -> assertFalse(instance.canRedo(), "not canRedo()"),
                () -> assertEquals(command, instance.lastDone(), "lastDone")
        );
    }

    /**
     * Test of did method, of class UndoRedo.
     */
    @Test
    public void testDidExecutedAfterUndo() {
        System.out.println("did, non-empty Redo stack");
        // set it up
        instance.did(new TestCommand());
        instance.undo(true);
        assertTrue(instance.canRedo(), "canRedo(), after did(), undo(true)");
        // Redo stack is not empty

        instance.did(command);
        assertAll(
                () -> assertTrue(instance.canUndo(), "canUndo()"),
                // Especially this:
                () -> assertFalse(instance.canRedo(), "not canRedo(), Redo stack cleared"),
                () -> assertEquals(command, instance.lastDone(), "lastDone")
        );
    }

    /**
     * Test of did method for exceptions, of class UndoRedo.
     */
    @Test
    public void testDidNotExecuted() {
        System.out.println("did, command not yet executed");
        instance.did(command);
        assertAll(
                () -> assertTrue(command.isExecuted(), "isExecuted"),
                () -> assertTrue(instance.canUndo(), "canUndo()"),
                () -> assertFalse(instance.canRedo(), "not canRedo()"),
                () -> assertEquals(command, instance.lastDone(), "lastDone")
        );
    }

    /**
     * Calls and checks undo().
     *
     * @param redoable parameter for {@code undo()}
     */
    private void checkUndo(final boolean redoable) {
        final StringBuilder trace = new StringBuilder();
        final Command command = new TestCommand() {
            @Override
            public void revert() {
                super.revert();
                trace.append("R");
            }
        };
        instance.did(command);
        instance.undo(redoable);
        assertAll(
                () -> assertFalse(command.isExecuted(), "not isExecuted"),
                () -> assertFalse(instance.canUndo(), "not canUndo"),
                () -> assertEquals(redoable, instance.canRedo(), "canRedo() after undo("
                        + redoable + ")"),
                () -> {
                    if (redoable) {
                        assertEquals(command, instance.lastUndone(), "lastUndone");
                    }
                    // else: pass
                },
                () -> assertEquals("R", trace.toString(), "Trace")
        );
    }

    /**
     * Test of undo method, of class UndoRedo.
     */
    @Test
    public void testUndoRedoable() {
        System.out.println("undo(true)");
        checkUndo(true);
    }

    /**
     * Test of undo method, of class UndoRedo.
     */
    @Test
    public void testUndoNotRedoable() {
        System.out.println("undo(false)");
        checkUndo(false);
    }

    /**
     * Test of Undo method for exceptions, of class UndoRedo.
     */
    @Test
    public void testUndoExceptions() {
        System.out.println("undo exceptions");
        Throwable e = assertThrows(IllegalStateException.class, () -> instance.undo(true));
        assertNotNull(e.getMessage(), "message should not be null");
    }

    /**
     * Test of redo method, of class UndoRedo.
     */
    @Test
    public void testRedo() {
        System.out.println("redo");
        final StringBuilder trace = new StringBuilder();
        final Command command = new TestCommand() {
            @Override
            public void execute() {
                super.execute();
                trace.append("E");
            }
        };
        instance.did(command);
        instance.undo(true);
        instance.redo();
        assertAll(
                () -> assertTrue(command.isExecuted(), "isExecuted"),
                () -> assertEquals("EE", trace.toString(), "Trace"),
                () -> assertEquals(command, instance.lastDone(), "lastDone")
        );
    }

    /**
     * Test of Undo method for exceptions, of class UndoRedo.
     */
    @Test
    public void testLastRedoExceptions() {
        System.out.println("redo exceptions");
        Throwable e = assertThrows(IllegalStateException.class, instance::redo);
        assertNotNull(e.getMessage(), "message should not be null");
    }

    /**
     * Calls and checks undoAll().
     *
     * @param redoable parameter for {@code undo()}
     */
    private void checkUndoAll(final boolean redoable) {
        final StringBuilder trace = new StringBuilder();
        for (final String message : new String[]{"A", "B", "C"}) {
            final Command command = new TestCommand() {
                @Override
                public void execute() {
                    super.execute();
                    trace.append('+').append(message);
                }

                @Override
                public void revert() {
                    super.revert();
                    trace.append('-').append(message);
                }
            };
            instance.did(command);
        }
        String expResult = "+A+B+C";
        assertEquals(expResult, trace.toString(), "Trace before undoAll");

        instance.undoAll(redoable);

        assertFalse(instance.canUndo(), "not canUndo(), after undoAll");
        expResult += "-C-B-A";
        assertEquals(expResult, trace.toString(), "Trace after undoAll");

        if (redoable) {
            // check that they are redoable
            while (instance.canRedo()) {
                instance.redo();
            }
            expResult += "+A+B+C";
            assertEquals(expResult, trace.toString(), "Trace after redo");
        } else {
            assertFalse(instance.canRedo(), "not canRedo(), after undoAll(false)");
        }
    }

    /**
     * Test of undoAll method, of class UndoRedo.
     */
    @Test
    public void testUndoAllRedoable() {
        System.out.println("undoAll(true)");
        checkUndoAll(true);
    }

    /**
     * Test of undo method, of class UndoRedo.
     */
    @Test
    public void testUndoAllNotRedoable() {
        System.out.println("undoAll(false)");
        checkUndoAll(false);
    }

    /**
     * Test of redoAll method, of class UndoRedo.
     */
    @Test
    public void testRedoAll() {
        System.out.println("redoAll");
        final StringBuilder trace = new StringBuilder();
        for (final String message : new String[]{"A", "B", "C"}) {
            final Command command = new TestCommand() {
                @Override
                public void execute() {
                    super.execute();
                    trace.append('+').append(message);
                }

                @Override
                public void revert() {
                    super.revert();
                    trace.append('-').append(message);
                }
            };
            instance.did(command);
        }
        instance.undoAll(true);
        String expResult = "+A+B+C-C-B-A";
        assertEquals(expResult, trace.toString(), "Trace after undo");

        instance.redoAll();

        assertFalse(instance.canRedo(), "not canRedo(), after redoAll");
        expResult += "+A+B+C";
        assertEquals(expResult, trace.toString(), "Trace after redoAll");
        // check that they are undoable
        while (instance.canUndo()) {
            instance.undo(true);
        }
        expResult += "-C-B-A";
        assertEquals(expResult, trace.toString(), "Trace after redoAll");
    }

    private class TestCommand extends Command {

        public TestCommand() {
            super(false);
        }

    }

}
