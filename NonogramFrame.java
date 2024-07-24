    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.ArrayList;
    import java.util.Arrays;
    
 class NonogramFrame extends JPanel {
        protected JTextArea txtarea = null;
        private PuzzleCell[][] puzzleBtns;
        protected Timer timer;
        protected int seconds;
        private JLabel timerLabel =  new JLabel();
    
        /**
         * Creates a panel for the Nonogram Puzzle Game GUI.
         *
         * @param gui    the Nonogram GUI
         * @param Widths  the widths of a Grid
         * @param Heights the hieght of a Grid
         * @param Move  theset of or list of the moves made in a puzzle
         */
        public NonogramFrame(NonogramUI gui, int height, int width, ArrayList<Assign> moves) {
            //Sets the layout and also the size of the panel
            boolean fullSize = true;
            txtarea = new JTextArea(10, 0);
            // Create the btnPanel
            JPanel displaybtnPanel = new JPanel();
            displaybtnPanel.setBackground(new Color(230,230,230)); 
        /**
         * Reverts the previous move in the puzzle changing the grids state of the Puzzle
         * 
         *@param frame the UI instance of puzzle
         * */
          // Create and customizes the "Undo" button
            JButton undobttn = new JButton("Undo");
            undobttn.setBackground(Color.BLACK); 
            undobttn.setForeground(Color.RED); 
            undobttn.setPreferredSize(new Dimension(92, 35));
            Font buttonFont1 = new Font(undobttn.getFont().getName(), Font.PLAIN, 17);
            undobttn.setFont(buttonFont1);
            undobttn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!gui.puzzle.isSolved()) {
                        if (!moves.isEmpty()) {
                            Assign last = moves.get(moves.size() - 1);
                            gui.puzzle.setState(last);
                            moves.remove(moves.get(moves.size() - 1));
                            puzzleBtns[last.getCol()][last.getRow()].setState(last.getPreviousState());
                            txtarea.append("Undo Cell \n");
                        }
                    }
                }
            });
     // Create and customzes the "Clear" button
            JButton clearbttn = new JButton("Clear");
            clearbttn.setBackground( new Color(0,0,0)); 
            clearbttn.setForeground(Color.RED); 
            clearbttn.setPreferredSize(new Dimension(92, 35));
            Font buttonFont2 = new Font(clearbttn.getFont().getName(), Font.PLAIN, 16);
            clearbttn.setFont(buttonFont2);
            clearbttn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clear(gui, width, height);
                    moves.clear();
                    txtarea.setText("\nCleared The Entire Puzzle\n");
                }
            });
    // Create and customizes the "Help" button
            JButton helpbttn = new JButton("Help");
            helpbttn.setBackground(new Color(0,0,0)); 
            helpbttn.setForeground(Color.RED); 
            helpbttn.setPreferredSize(new Dimension(92, 35));
            Font buttonFont5 = new Font(helpbttn.getFont().getName(), Font.PLAIN, 17);
            helpbttn.setFont(buttonFont5);
            helpbttn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NonogramUI.help(txtarea);
                }
            });
     // Create and customizes the "Save" button
            JButton savesbttn = new JButton("Save");
            savesbttn.setBackground(new Color(0,0, 0)); 
            savesbttn.setForeground(Color.RED); 
            savesbttn.setPreferredSize(new Dimension(92, 35));
            Font buttonFont3 = new Font(savesbttn.getFont().getName(), Font.PLAIN, 17);
            savesbttn.setFont(buttonFont3);
            savesbttn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!gui.puzzle.isSolved()) {
                        gui.save();
                        txtarea.append("Saved Currently Solved Puzzle Game \n");
                    }
                }
            });
     // Create and customizes the "Load" button
            JButton loadbttn = new JButton("Load");
            loadbttn.setBackground(new Color(0,0,0)); 
            loadbttn.setForeground(Color.RED); 
            loadbttn.setPreferredSize(new Dimension(92, 35));
            Font buttonFont4 = new Font(loadbttn.getFont().getName(), Font.PLAIN, 17);
            loadbttn.setFont(buttonFont4);
            loadbttn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gui.puzzle.isSolved()) {
                if (gui.load()) {
                    int i = 0;
                    do {
                        int j = 0;
                        do {
                            puzzleBtns[i][j].setState(gui.puzzle.getState(j, i));
                            j++;
                        } while (j < height);
                        i++;
                    } while (i < width);
                    txtarea.append("Loaded Your Saved Puzzle Game \n");
                }
            }
        }
    });
    
     
        //Buttons are added to the displaybtnpanel
            displaybtnPanel.add(undobttn);
            displaybtnPanel.add(clearbttn);
            displaybtnPanel.add(savesbttn);
            displaybtnPanel.add(loadbttn);
            displaybtnPanel.add(helpbttn);
    
            // Create the timer label
            timerLabel = new JLabel("Time: 0 seconds");
            timerLabel.setForeground(new Color(0,101,0)); 
    
            Font customFont = new Font("MONOSPACED", Font.ITALIC, 30);
            timerLabel.setFont(customFont);
    
     
            // Create the text area panel to display user operations details
            txtarea.setEditable(false);
    
            // Create the scroll pane
            JScrollPane txtAreaScroll = new JScrollPane(txtarea);
            txtAreaScroll.setMaximumSize(new Dimension(480, 400));
            txtAreaScroll.setPreferredSize(new Dimension(480, 400));
            txtAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
            // Create the grid panel
            // Original insets
           int originalInsets = 1;
    
         // Calculate the new insets as 1.5 times the original size
                 int newInsets = (int) (originalInsets * 1.75);
    
         // Create the grid panel
           JPanel gridPanel = new JPanel();
           gridPanel.setLayout(new GridBagLayout());
           GridBagConstraints c = new GridBagConstraints();
           c.insets = new Insets(newInsets, newInsets, newInsets, newInsets);
    
    
            // Create the row label and column label for both x-axis and  y-axis in grid
            JTextArea[] xLabels = new JTextArea[height];
            JTextArea[] yLabels = new JTextArea[width];
            for (int i = 0; i < height; i++) {
                int[] colNums = gui.puzzle.getColNums(i);
                StringBuilder colNumsString = new StringBuilder();
                for (int num : colNums) {
                  colNumsString.append(num).append("\n");
                  }
                  String nextCol = colNumsString.toString();
                  int[] rowNums = gui.puzzle.getRowNums(i);
                  StringBuilder rowNumsString = new StringBuilder();
                  for (int num : rowNums) {
                      rowNumsString.append(num).append(" ");
                      }
                      String nextRow = rowNumsString.toString();
                      xLabels[i] = new JTextArea(nextCol);
                      yLabels[i] = new JTextArea(nextRow);
                      xLabels[i].setOpaque(false);
                      yLabels[i].setOpaque(false);
                      c.gridx = 0;
                      c.gridy = i + 1;
                      gridPanel.add(yLabels[i], c);
                      Font boldFont1 = new Font(yLabels[i].getFont().getName(), Font.BOLD, 12);
                      yLabels[i].setFont(boldFont1);
                      yLabels[i].setForeground(Color.BLUE);
            }
    
            // Create row and columns labels for puzzle grid
            for (int columnIndex = 0; columnIndex < width; columnIndex++) {
        c.gridx = columnIndex + 1;
        c.gridy = 0;
        gridPanel.add(xLabels[columnIndex], c);
        Font boldFont = new Font(xLabels[columnIndex].getFont().getName(), Font.BOLD, 10);
        xLabels[columnIndex].setFont(boldFont);
        xLabels[columnIndex].setForeground(Color.BLUE);
    }
    
    
            // Buttons for puzzle cells
            puzzleBtns = new PuzzleCell[height][width];
            int i = 0;
            while (i < width) {
                int j = 0;
                while (j < height) {
            puzzleBtns[i][j] = new PuzzleCell(gui, i, j, 2, moves, txtarea);
            puzzleBtns[i][j].setPreferredSize(new Dimension(!fullSize ? 55 : 27, !fullSize ? 55 : 27));
            puzzleBtns[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            c.gridx = i + 1;
            c.gridy = j + 1;
            gridPanel.add(puzzleBtns[i][j], c);
            j++;
        }
        i++;
    }
    
    
            displaybtnPanel.setPreferredSize(new Dimension(550, 600));
            // Add the above items to panel
            displaybtnPanel.add(txtAreaScroll);
            add(displaybtnPanel, BorderLayout.WEST);
            add(gridPanel, BorderLayout.CENTER);
            displaybtnPanel.add(timerLabel);
        
    
    
     // Create the timer
            seconds = 0;
            timer = new Timer(999, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    seconds++;
                     int totalMinutes = seconds / 60;
                     int hours = totalMinutes / 60;
                     int mins = totalMinutes % 60;
                     int remainingSeconds = seconds % 60;
    
                     String timeString = hours + ":" + String.format("%02d", mins) + ":" + String.format("%02d", remainingSeconds);
    
                    timerLabel.setText("YOUR GAME TIME: " + timeString);
                    if (gui.puzzle.isSolved()) {
                        timer.stop();
                    }
                }
            });
            timer.start();
           }
    //Function or Method to reset the puzzle and timer
       private void clear(NonogramUI ui, int width, int height) {
        ui.puzzle.clear();
    
        int i = 0;
        do {
            int j = 0;
            do {
                puzzleBtns[i][j].setState(2);
                j++;
            } while (j < height);
            i++;
        } while (i < width);
    
        timer.stop();
        seconds = 0;
        timerLabel.setText("TIME: 0 seconds");
        timer.start();
        }
       }
       
    
