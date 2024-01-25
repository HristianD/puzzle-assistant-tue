package ypa.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import ypa.model.KSCell;
import ypa.model.KSPuzzle;

/**
 * A graphical view on a Kakuro puzzle state.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class PuzzlePanel extends javax.swing.JPanel {

    /**
     * Creates new form PuzzlePanel.
     */
    public PuzzlePanel() {
        initComponents();
        initPanel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    /** Cell size in pixels */
    private final int cellSize = 80;
    // TODO: Consider making the cell size changeable by user

   final int offsetX = cellSize; // margin for horizontal coordinates
   final int offsetY = cellSize; // margin for vertical coordinates

   /** The puzzle being manipulated */
    private KSPuzzle puzzle;

    /** Selected cell, affected by keystrokes. */
    private KSCell selected;

    /** Whether symbols are highlighted */
    private boolean highlight;

    /** Marked cells (by different background color) */
    private Set<KSCell> markedCells;

    /**
     * Initializes this panel.
     */
    private void initPanel() {
        setPuzzle(null);
        highlight = true;
    }

    /**
     * Sets the puzzle.
     *
     * @param puzzle  the puzzle
     */
    public void setPuzzle(final KSPuzzle puzzle) {
        this.puzzle = puzzle;
        this.selected = null;
        this.markedCells = null;
    }

    /**
     * Gets selected cell.
     *
     * @return the selected cell
     */
    public KSCell getSelected() {
        return selected;
    }

    /**
     * Sets selected cell.
     *
     * @param cell  the selected cell
     */
    public void setSelected(final KSCell cell) {
        this.selected = cell;
    }

    /**
     * Sets whether to highlight the marked cells.
     *
     * @param newState  the new highlighting state
     */
    public void setHighlight(final boolean newState) {
        this.highlight = newState;
    }

    /**
     * Sets the marked cells, which will be highlighted if enabled.
     *
     * @param markedCells  the cells to mark, or {@code null} if none
     */
    public void setMarkedCells(final Collection<KSCell> markedCells) {
        if (markedCells == null) {
            this.markedCells = new HashSet<>();
        } else {
            this.markedCells = new HashSet<>(markedCells);
        }
    }

    /**
    /**
     * Draws given cell on given canvas at given location.
     *
     * @param g  Graphics object to draw on
     * @param cell  cell to draw
     * @param x  x-coordinate for bottom left corner of cell
     * @param y  y-coordinate for bottom left corner of cell
     * @param delta_x  x-offset for digit within cell
     * @param delta_y  y-offset for digit within cell
     */
    private void paintCell(final Graphics g, final KSCell cell,
            final int x, final int y, final int delta_x, final int delta_y) {
        // set background if cell is marked
        if (highlight && this.markedCells != null
                && this.markedCells.contains(cell)) {
            g.setColor(Color.CYAN);
            g.fillRect(x + 1, y - cellSize + 1,
                    cellSize - 1, cellSize - 1);
        }
        if (selected != null && cell == selected) {
            g.setColor(Color.YELLOW);
            g.fillRect(x + 1, y - cellSize + 1,
                    cellSize - 1, cellSize - 1);
        }
        if (!cell.isEmpty(cell)) {
            // cell occupied by a symbol
            // set color for symbol
            Color color = Color.BLACK;
            if (highlight) {
                if (!puzzle.isValid(cell)) {
                    color = Color.RED;
                } else if (puzzle.isSolved(cell)) {
                    color = Color.GREEN;
                }
            }
        }
        
        Color colorCell = cell.getColor();
        g.setColor(colorCell);
        g.fillRect(x + 1, y - cellSize + 1,
                    cellSize - 1, cellSize - 1);
        
        int groupNumber = cell.getCageSum();
        if (groupNumber > 0) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("default", Font.BOLD, 12)); // change the font size to make the group number smaller
            g.drawString(String.valueOf(groupNumber), x + 5, y - cellSize + 15);
        }
        
        g.setColor(Color.BLACK);
        g.drawString(cell.toString(), x + delta_x, y - delta_y);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(java.awt.Color.DARK_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 24));
        if (puzzle == null) {
            g.setColor(Color.YELLOW);
            g.drawString("No puzzle loaded", cellSize, cellSize);
            return;
        }
        // puzzle != null
        final int WIDTH = cellSize * puzzle.getColumnCount();
        final int HEIGHT = cellSize * puzzle.getRowCount();
        // WIDTH, HEIGHT includes top, left border for showing coordinates
        FontMetrics fm = g.getFontMetrics();
        final int delta_x = (cellSize - fm.charWidth('0')) / 2 + 1;
        final int delta_y = (cellSize - fm.getAscent() + fm.getDescent()) / 2;

        g.setColor(Color.WHITE);
        g.fillRect(cellSize, cellSize, WIDTH, HEIGHT);
        g.setColor(java.awt.Color.BLACK);
        // draw all horizontal separator lines
        for (int r = 0; r <= puzzle.getRowCount(); ++ r) {
            final int y = r * cellSize + offsetY;
            g.drawLine(offsetX, y, WIDTH + offsetX, y);
        }
        // draw all vertical separator lines
        for (int c = 0; c <= puzzle.getColumnCount(); ++ c) {
            final int x = c * cellSize + offsetX;
            g.drawLine(x, offsetY, x, HEIGHT + offsetY);
        }
        // draw cell background and contents
        for (int r = 0; r != puzzle.getRowCount(); ++ r) {
            final int y = (r + 1) * cellSize + offsetY;
            for (int c = 0; c != puzzle.getColumnCount(); ++ c) {
                final int x = c * cellSize + offsetX;
                // x, y = coordinate of bottom-left corner
                final KSCell cell = puzzle.getCell(r, c);
                paintCell(g, cell, x, y, delta_x, delta_y);
            }
        }
        // draw entries sums
        g.setColor(Color.WHITE);
        g.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 12));
    }

    /**
     * Returns cell in grid for a given mouse event.
     *
     * @param evt  the mouse event
     * @return  cell in grid at {@code evt}, or {@code null} if non-existent
     * @pre {@code evt != null}
     */
    public KSCell mouseToCell(final MouseEvent evt) {
        if (puzzle == null) {
            return null;
        }
        final int row = (evt.getY() - offsetY) / cellSize;
        final int col = (evt.getX() - offsetX) / cellSize;
        if (puzzle.has(row, col)) {
            return puzzle.getCell(row, col);
        } else {
            return null;
        }
    }

}
