import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PuzzleCell extends JButton {
     //constants for cells in the Nonogram puzzle
    private int RED = 000;
    private int BLACK = 001;
    private int UNASSIGNED = 002;
    
      //Current State of cell, 000 for white and 001 for black and 002 for unassigned 

    private int presentState = UNASSIGNED;
    
    private final int x;
    //The coordinates-X of  cell in  Nonogram puzzle.
    private final int y;
    //The coordinates-Y of  cell in  Nonogram puzzle.

 /**
 * Creates a new instance of a puzzle cell with a specified position and its respective state.
 *
 * @param ui the user interface context to which a cell will belong
 * @param 'X' is  coordinate of X in a puzzle cell within the Nonogram puzzle
 * @param 'Y, is  coordinate of Y  in a puzzle cell within the Nonogram puzzle
 * @param state is an initial state of the puzzle cell
 */

    public PuzzleCell(NonogramUI ui, int x, int y, int state, ArrayList<Assign> moves, JTextArea txtarea) {
        this.x = x;
        this.y = y;
        this.presentState = state;
        setOpaque(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleCellClick(ui, moves, txtarea); //To Check whether a puzzle is solved or not
            }
        });
    }
//Manages and Handles the Cell click action
    private void handleCellClick(NonogramUI ui, ArrayList<Assign> moves, JTextArea txtarea) {
        if (!ui.puzzle.isSolved()) {
            int previousState = ui.puzzle.getState(y, x);
            toggleCellState();
            setBackground(determineColoursOfCellStates());
            Assign cellAssignment = new Assign(y, x, presentState);
            cellAssignment.setPreviousState(previousState);
            moves.add(cellAssignment);
            ui.puzzle.setState(y, x, presentState);
        }
        if (ui.puzzle.isSolved()) {
            txtarea.append("Congratulations! Your Puzzle is Solved Successfully.\nPress The Clear Button To Solve A New Puzzle.\n");
        }
    }
/**
 * 
 *Sets the state of the cell
 *
 */
    private void toggleCellState() {
        presentState = (presentState + 1) % 3;
    }
/**
 *Retrieves the Color that has to be applied for the Current State of the button
 *
@return the color corresponding towards the Current State of a button
 */
    private Color determineColoursOfCellStates() {
    Color[] stateColors = new Color[]{
        Color.RED,
        Color.BLACK,
        new Color(238, 238, 238)
    };

    if (presentState >= 0 && presentState < stateColors.length) {
        return stateColors[presentState];
    } else {
        
        return new Color(238, 238, 238);
    }
}
/**
 * Updates or refreshes the States of the button
 * @param states the revised and the updated state of the buttons
 */

    public void setState(int state) {
        presentState = state;
        setBackground(determineColoursOfCellStates());
    }
}

