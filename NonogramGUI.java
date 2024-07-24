
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class NonogramGUI
{
    //Stores the moves of cells in the puzzle
    private ArrayList<Assign> moves = new ArrayList<>();
    
    
    //Initializes the GUI for the puzzle.
    public void initilizePanel(NonogramUI ui){
        JFrame frame = new JFrame("Nonogram Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NonogramFrame panel = new NonogramFrame(ui,ui.puzzle.getNumCols(),ui.puzzle.getNumRows(),moves);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
