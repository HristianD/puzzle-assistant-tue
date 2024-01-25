// package ypa.reasoning;

// import ypa.command.CompoundCommand;
// import ypa.command.SetCommand;
// import ypa.model.KCell;
// import ypa.model.KPuzzle;

// /**
//  * When only one way of filling an empty cell does not lead to an invalid state
//  * after applying a given reasoner,
//  * then that one way of filling is forced.
//  * This generalizes both the {@link EntryWithOneEmptyCell} reasoner,
//  * and the {@link BasicEmptyCellByContradiction} reasoner.
//  * Note that it is specifically allowed to set the reasoner used
//  * for validity checking, to the reasoner itself.
//  * That is why the reasoner is not set in the constructor.
//  *
//  * @author Tom Verhoeff (Eindhoven University of Technology)
//  */
// public class GeneralizedEmptyCellByContradiction extends EmptyCellReasoner {

//     /** The reasoner to apply before checking validity. */
//     private final Reasoner reasoner;

//     /**
//      * Constructs a reasoner for the given puzzle and reasoner.
//      *
//      * @param puzzle  the puzzle to reason about
//      * @param reasoner  the reasoner to use before validity checking
//      * @throws IllegalArgumentException  if precondition failed
//      * @pre {@code puzzle != null  && reasoner != null && reasoner.puzzle == puzzle}
//      */
//     public GeneralizedEmptyCellByContradiction(KPuzzle puzzle, final Reasoner reasoner) {
//         super(puzzle);
//         if (reasoner == null) {
//             throw new IllegalArgumentException(this.getClass().getSimpleName()
//                     + "setReasoner.pre failed: reasoning == null");
//         }
//         if (reasoner.puzzle != this.puzzle) {
//             throw new IllegalArgumentException(this.getClass().getSimpleName()
//                     + "setReasoner.pre failed: reasoning.puzzle != this.puzzle");
//         }
//         this.reasoner = reasoner;
//     }

//     // /**
//     //  * Constructs self-referencing (recursive) reasoner.
//     //  *
//     //  * @param puzzle  the puzzle to reason about
//     //  * @throws IllegalArgumentException  if precondition failed
//     //  * @pre {@code puzzle != null}
//     //  */
//     // public GeneralizedEmptyCellByContradiction(KPuzzle puzzle) {
//     //     super(puzzle);
//     //     reasoner = this;
//     // }

//     // @Override
//     // CompoundCommand applyToCell(final KCell cell) throws NullPointerException {
//     //     if (!puzzle.isValid()) {
//     //         return null;
//     //     }
//     //     CompoundCommand result = super.applyToCell(cell);
//     //     CompoundCommand candidateForcedCommand = null; // command that worked, if any

//     //     for (int state = puzzle.getMinNumber(); state <= puzzle.getMaxNumber(); ++state) {
//     //         CompoundCommand command = new CompoundCommand();
//     //         command.add(new SetCommand(cell, state));
//     //         command.execute();
//     //         CompoundCommand compound = reasoner.apply();
//     //         if (compound != null) {
//     //             // no contradiction
//     //             command.add(compound);
//     //         }
//     //         command.revert();
//     //         if (compound != null) {
//     //             // no contraction; command is a candidate
//     //             if (candidateForcedCommand == null) {
//     //                 // first command that is valid; memorize it
//     //                 candidateForcedCommand = command;
//     //             } else {
//     //                 // multiple valid ways of filling cell; no forced command
//     //                 return result;
//     //             }
//     //         }
//     //     }
//     //     // at most one command worked

//     //     if (candidateForcedCommand == null) {
//     //         // all commands failed: puzzle not solvable
//     //         return null;
//     //     } else {
//     //         // exactly one command worked
//     //         result.add(candidateForcedCommand);
//     //         return result;
//     //     }
//     // }

// }
