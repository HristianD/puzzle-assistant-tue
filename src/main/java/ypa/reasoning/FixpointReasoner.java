// package ypa.reasoning;

// import ypa.command.CompoundCommand;
// import ypa.model.KPuzzle;

// /**
//  * A reasoner that repeatedly applies a given reasoner
//  * until the least fixed point is reached (a closure operation).
//  * That is, the reasoner is repeated until either there is no further change,
//  * or until an invalid state is reached,
//  * in which case all previous changes are reverted.
//  *
//  * @author Tom Verhoeff (Eindhoven University of Technology)
//  */
// public class FixpointReasoner extends ReasonerDecorator {

//     public FixpointReasoner(KPuzzle puzzle, Reasoner reasoner) {
//         super(puzzle, reasoner);
//     }

//     @Override
//     public CompoundCommand apply() {
//         final CompoundCommand result = super.apply();

// // repeatedly apply reasoner until no change occurs
//         CompoundCommand compound;
//         do {
//             compound = reasoner.apply();
//             if (compound == null) {
//                 result.revert(); // HARD-TO-FIND DEFECT IF OMITTED!
//                 return compound;
//             }
//             result.addAll(compound);
//         } while (compound.size() > 0);
// //

//         return result;
//     }

// }
