// package ypa.reasoning;

// import java.util.ArrayList;
// import java.util.List;

// import ypa.command.CompoundCommand;
// import ypa.model.KPuzzle;

// /**
//  * Applies multiple reasoners in sequence.
//  * The sequence reflects the order in which reasoners were added.
//  * Based on the Composite design pattern.
//  *
//  * @author Tom Verhoeff (Eindhoven University of Technology)
//  */
// public class CompoundReasoner extends Reasoner {

//     /** The sequence of reasoners. */
//     private final List<Reasoner> reasoners = new ArrayList<>();

//     public CompoundReasoner(KPuzzle puzzle) {
//         super(puzzle);
//     }

//     /**
//      * Adds a reasoner.
//      *
//      * @param reasoner  reasoner to add
//      * @pre {@code reasoner != null} and already set for {@code puzzle}
//      * @throws IllegalArgumentException  if {@code strategy ! null ||
//      *     strategy} is not for same puzzle
//      */
//     public void add(final Reasoner reasoner) {
//         if (reasoner == null) {
//             throw new IllegalArgumentException(this.getClass().getSimpleName()
//                     + ".add().pre failed: reasoning == null");
//         }
//         reasoners.add(reasoner);
//     }

//     @Override
//     public CompoundCommand apply() {

// //  Apply sequence of reasoners until first change
//         for (Reasoner reasoner : reasoners) {
//             final CompoundCommand command = reasoner.apply();
//             if (command == null) {
//                 return command;
//             } else if (command.size() > 0) {
//                 return command;
//             }
//         }
// //

//         return super.apply();
//     }

// }
