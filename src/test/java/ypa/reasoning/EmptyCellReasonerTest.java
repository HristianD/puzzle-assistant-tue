//package ypa.reasoning;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import ypa.command.CompoundCommand;
//import ypa.model.KCell;
//import ypa.model.KPuzzle;
//import ypa.reasoning.EmptyCellReasoner;
//
//import java.util.Scanner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Test cases for {@link EmptyCellReasoner}.
// *
// * @author wstomv
// */
//public class EmptyCellReasonerTest {
//
//    private KPuzzle puzzle;
//
//    @BeforeEach
//    public void setUp() {
//        puzzle = new KPuzzle(new Scanner(ReasonerTest.PUZZLE), "Test");
//    }
//
//    /**
//     * Test of applyToCell method, of class EmptyCellReasoner.
//     */
//    @Test
//    public void testApplyToCell() {
//        System.out.println("applyToCell");
//        EmptyCellReasoner instance = new EmptyCellReasonerImpl(puzzle);
//        KCell cell = puzzle.getCell(1, 1);
//        CompoundCommand result = instance.applyToCell(cell);
//        assertAll(
//                () -> assertEquals(0, result.size(), "result.size()"),
//                () -> assertFalse(result.isExecuted(), "result.executed")
//        );
//    }
//
//    /**
//     * Test of apply method, of class EmptyCellReasoner.
//     */
//    @Test
//    public void testApply() {
//        System.out.println("apply");
//        EmptyCellReasoner instance = new EmptyCellReasonerImpl(puzzle);
//        CompoundCommand result = instance.apply();
//        assertAll(
//                () -> assertEquals(0, result.size(), "result.size()"),
//                () -> assertTrue(result.isExecuted(), "result.executed")
//        );
//    }
//
//    private static class EmptyCellReasonerImpl extends EmptyCellReasoner {
//        public EmptyCellReasonerImpl(KPuzzle puzzle) {
//            super(puzzle);
//        }
//    }
//
//}
