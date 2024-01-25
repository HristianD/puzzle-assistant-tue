// package ypa.reasoning;

// import ypa.model.KPuzzle;

// /**
//  * Abstract base class for reasoner decorators, holding common code.
//  *
//  * @author Tom Verhoeff (Eindhoven University of Technology)
//  */
// public abstract class ReasonerDecorator extends Reasoner {

//     /** The reasoner being decorated. */
//     protected Reasoner reasoner;

//     /* Rep. invariant
//      *  reasoner.puzzle == this.puzzle
//      */

//     /**
//      * Constructs a reasoner for the given puzzle and reasoner.
//      *
//      * @param puzzle  the puzzle to reason about
//      * @param reasoner  the reasoner to use before validity checking
//      * @throws IllegalArgumentException  if precondition failed
//      * @pre {@code puzzle != null  && reasoner != null && reasoner.puzzle == puzzle}
//      */
//     public ReasonerDecorator(KPuzzle puzzle, final Reasoner reasoner) {
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

// }
