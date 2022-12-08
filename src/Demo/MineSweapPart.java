package Demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.concurrent.ThreadLocalRandom;

public class MineSweapPart extends JFrame {
    private static final LocalTime STARTTIME = LocalTime.now();
    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 760;
    private static final int WIDTH = 760;
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int MINES = 8;
    private static int minesLeft = MineSweapPart.MINES;
    private static int actualMinesLeft = MineSweapPart.MINES;
    private static final String FLAGGED = "@";
    private static final String MINE = "M";
    // visual indication of an exposed MyJButton
    private static final Color expColor = Color.lightGray;
    // colors used when displaying the getStateStr() String
    private static final Color colorMap[] = {Color.lightGray, Color.blue, Color.green, Color.cyan, Color.yellow,
            Color.orange, Color.pink, Color.magenta, Color.red, Color.red};
    private boolean running = true;
    // holds the "number of mines in perimeter" value for each MyJButton
    private int[][] sGrid = new int[ROWS][COLS];

    public MineSweapPart() {
        this.setTitle("MineSweap " +
                MineSweapPart.minesLeft + " Mines left");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLayout(new GridLayout(ROWS, COLS, 0, 0));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.createContents();
// place MINES number of mines in sGrid and adjust all of the "mines in perimeter" values
        this.setMines();
        this.setVisible(true);
    }

    public void createContents() {
        for (int br = 0; br < ROWS; ++br) {
            for (int bc = 0; bc < COLS; ++bc) {
// set sGrid[br][bc] entry to 0 - no mines in it’s perimeter
                sGrid[br][bc] = 0;
// create a MyJButton that will be at location (br, bc) in the GridLayout
                MyJButton but = new MyJButton("", br, bc);
// register the event handler with this MyJbutton
                but.addActionListener(new MyListener());
// add the MyJButton to the GridLayout collection
                this.add(but);
            }
        }
    }


    // nested private class
    private class MyListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (running) {
// used to detrmine if ctrl or alt key was pressed at the time of mouse action
                int mod = event.getModifiers();
                MyJButton jb = (MyJButton) event.getSource();
// is the MyJbutton that the mouse action occurred in flagged
                boolean flagged = jb.getText().equals(MineSweapPart.FLAGGED);
// is the MyJbutton that the mouse action occurred in already exposed
                boolean exposed = jb.getBackground().equals(expColor);
// flag a cell : ctrl + left click
                if (!flagged && !exposed && (mod & ActionEvent.CTRL_MASK) != 0) {
// If you are out of flags do nothing
                    if (MineSweapPart.minesLeft == 0) {
                        return;
                    }
                    jb.setText(MineSweapPart.FLAGGED);
                    --MineSweapPart.minesLeft;
// if the MyJbutton that the mouse action occurred in is a mine
                    if (sGrid[jb.row][jb.col] == 9) {
                        MineSweapPart.actualMinesLeft--;
                        MineSweapPart.minesLeft--;
// If all the mines are flagged, exit
                        if (actualMinesLeft == 0)
                            JOptionPane.showMessageDialog(null,
                                    "You WIN! \n It took you " +
                                            MineSweapPart.STARTTIME.until(LocalTime.now(), MINUTES) +
                                            " minutes.");
                        running = false;
                    }
                    setTitle("MineSweap " +
                            MineSweapPart.minesLeft + " Mines left");
                }
// un-flag a cell : alt + left click
                else if (flagged && !exposed && (mod & ActionEvent.ALT_MASK) != 0) {
                    jb.setText("");
                    ++MineSweapPart.minesLeft;
// if the MyJbutton that the mouse action occurred in is a mine
                    if (sGrid[jb.row][jb.col] == 9) {
                        ++MineSweapPart.actualMinesLeft;
                    }
                    setTitle("MineSweap " +
                            MineSweapPart.minesLeft + " Mines left");
                }
// expose a cell : left click
                else if (!flagged && !exposed) {
                    exposeCell(jb);
                }
            }
        }

        public void exposeCell(MyJButton jb) {
            if (!running)
                return;
// expose this MyJButton
            jb.setBackground(expColor);
            jb.setForeground(colorMap[sGrid[jb.row][jb.col]]);
            jb.setText(getStateStr(jb.row, jb.col));
// if the MyJButton that was just exposed is a mine
            if (sGrid[jb.row][jb.col] == 9) {
                JOptionPane.showMessageDialog(null,
                        "Sorry, you lost! \n You found " +
                                (MineSweapPart.MINES - MineSweapPart.actualMinesLeft) + " mines!");
                running = false;
// Exit, the cell you exposed was a mine
            }
// if the MyJButton that was just exposed has no mines in its perimeter
            if (sGrid[jb.row][jb.col] == 0) {
// Need nested for loop to go through the entire array to look at your piremeter
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
// Check that x && y are not == 0
// if the for loops point to an inbound point
                        if (!(x == 0 && y == 0) &&
                                (jb.row + x >= 0) && (jb.row + x < MineSweapPart.ROWS) &&
                                (jb.col + y >= 0) && (jb.col + y < MineSweapPart.COLS)) {

                            int index = ((jb.row + x) * MineSweapPart.ROWS) + (jb.col + y);
                            MyJButton newButton = (MyJButton) (jb.getParent().getComponent(index));
// if the new button is not flagged and not exposed
                            if (!newButton.getText().equals(MineSweapPart.FLAGGED) &&
                                    !newButton.getBackground().equals(expColor)) {
                                exposeCell(newButton);
                            }

                        }
                    }
                }

            }
        }
    }


    // place MINES number of mines in sGrid and adjust all of the "mines in perimeter" values
    private void setMines() {
        for (int i = 0; i < MINES; i++) {
            int randRow = ThreadLocalRandom.current().nextInt(0, ROWS);
            int randCol = ThreadLocalRandom.current().nextInt(0, COLS);
            if (!(sGrid[randRow][randCol] == 9)) { // Check that this cell is not a mine
                sGrid[randRow][randCol] = 9;
// Check the row above the mine
                if (randRow + 1 < sGrid.length) {
                    if (sGrid[randRow + 1][randCol] != 9) {
                        sGrid[randRow + 1][randCol]++;
                    }
                    if (randCol + 1 < sGrid[randRow + 1].length && sGrid[randRow + 1][randCol + 1] != 9) {
                        sGrid[randRow + 1][randCol + 1]++;
                    }
                    if (randCol - 1 >= 0 && sGrid[randRow + 1][randCol - 1] != 9) {
                        sGrid[randRow + 1][randCol - 1]++;
                    }
                }

                if (randRow - 1 >= 0) {
                    if (sGrid[randRow - 1][randCol] != 9) {
                        sGrid[randRow - 1][randCol]++;
                    }
                    if (randCol + 1 < sGrid[randRow - 1].length && sGrid[randRow - 1][randCol + 1] != 9) {
                        sGrid[randRow - 1][randCol + 1]++;
                    }
                    if (randCol - 1 >= 0 && sGrid[randRow - 1][randCol - 1] != 9) {
                        sGrid[randRow - 1][randCol - 1]++;
                    }
                }
                if (randCol + 1 < sGrid[randRow].length && sGrid[randRow][randCol + 1] != 9) {
                    sGrid[randRow][randCol + 1]++;
                }
                if (randCol - 1 >= 0 && sGrid[randRow][randCol - 1] != 9) {
                    sGrid[randRow][randCol - 1]++;
                }
            } else {
                i--;
            }
        }


    }

    private String getStateStr(int row, int col) {
// no mines in this MyJbutton’s perimeter
        if (this.sGrid[row][col] == 0)
            return "";
// 1 to 8 mines in this MyJButton’s perimeter
        else if (this.sGrid[row][col] > 0 && this.sGrid[row][col] < 9)
            return "" + this.sGrid[row][col];
// this MyJButton in a mine
        else
            return MineSweapPart.MINE;
    }

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {

        } catch (ClassNotFoundException e) {

        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }

        new MineSweapPart();
    }
}

