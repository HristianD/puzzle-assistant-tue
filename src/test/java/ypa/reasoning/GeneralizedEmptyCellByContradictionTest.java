//package ypa.reasoning;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import ypa.command.CompoundCommand;
//import ypa.model.KCell;
//import ypa.model.KPuzzle;
//import ypa.reasoning.EmptyCellReasoner;
//import ypa.reasoning.EntryWithOneEmptyCell;
//import ypa.reasoning.GeneralizedEmptyCellByContradiction;
//import ypa.reasoning.Reasoner;
//
//import java.util.Scanner;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Test cases for {@link GeneralizedEmptyCellByContradiction}.
// *
// * @author wstomv
// */
//public class GeneralizedEmptyCellByContradictionTest {
//
//    private KPuzzle puzzle;
//
//    /**
//     * Prepares each test case.
//     */
//    @BeforeEach
//    public void setUp() {
//        puzzle = new KPuzzle(new Scanner(ReasonerTest.PUZZLE), "Test");
//        System.out.println(puzzle);
//        System.out.println(puzzle.gridAsString());
//    }
//
//    /**
//     * Test of applyToCell method, of class EntryWithOneEmptyCell.
//     */
//    @Test
//    public void testApplyToCell() {
//        System.out.println("applyToCell");
//        KCell cell11 = puzzle.getCell(1, 1);
//        cell11.setState(1);
//        KCell cell12 = puzzle.getCell(1, 2);
//        Reasoner reasoner = new EntryWithOneEmptyCell(puzzle);
//        EmptyCellReasoner instance = new GeneralizedEmptyCellByContradiction(puzzle, reasoner);
//        System.out.println(puzzle.gridAsString());
//        CompoundCommand result = instance.applyToCell(cell12);
//        System.out.println(puzzle.gridAsString());
//        assertAll(
//                () -> assertEquals(1, result.size(), "result.size()"),
//                () -> assertFalse(result.isExecuted(), "result.executed"),
//                () -> assertEquals(1, cell11.getState(), "cell 1, 1 state"),
//                () -> assertEquals(KCell.EMPTY, cell12.getState(), "cell 1, 2 state")
//        );
//    }
//
//    /**
//     * Test of apply method, of class EntryWithOneEmptyCell.
//     */
//    @Test
//    public void testApply() {
//        System.out.println("apply");
//        Reasoner reasoner = new EntryWithOneEmptyCell(puzzle);
//        EmptyCellReasoner instance = new GeneralizedEmptyCellByContradiction(puzzle, reasoner);
//        System.out.println(puzzle.gridAsString());
//        CompoundCommand result = instance.apply();
//        System.out.println(puzzle.gridAsString());
//        assertAll(
//                () -> assertEquals(1, result.size(), "result.size()"),
//                () -> assertTrue(result.isExecuted(), "result.executed")
//        );
//    }
//
//}
