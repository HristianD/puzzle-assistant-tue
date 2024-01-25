/**
 * Package containing the model of a Killer sudoku puzzle.
 * The class {@link KSPuzzle} also acts as a <em>façade</em> for this package.
 * However, also class {@link ypa.model.KSCell} is important:
 * <ul>
 * <li>To iterate over all cells:
 *      {@code for (KSCell cell : puzzle.getCells())}</li>
 * <li>To create a new {@link ypa.command.SetCommand}</li>
 * <li>To check whether a cell is empty:
 *      {@link ypa.model.KSCell#isEmpty()}</li>
 * <li>To obtain the empty cell state: {@link ypa.model.KSCell#EMPTY}</li>
 * </ul>
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
package ypa.model;
