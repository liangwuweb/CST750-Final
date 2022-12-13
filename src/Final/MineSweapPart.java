package Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MineSweapPart extends JFrame
{
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_HEIGHT = 760;
  private static final int WINDOW_WIDTH = 760;
  private static final int TOTAL_MINES = 8;
  
  
  private static int guessedMinesLeft;
  private static int actualMinesLeft;

  private static final String INITIAL_CELL_TEXT = "";
  //private static final ImageIcon INITIAL_CELL_ICON = new ImageIcon();
  private static final String UNEXPOSED_FLAGGED_CELL_TEXT = "@";
  private static final ImageIcon UNEXPOSED_FLAGGED_CELL_ICON = new ImageIcon("flag.png");
  private static final String EXPOSED_MINE_TEXT = "M";
  
  // visual indication of an exposed Final.MyJButton

  private static final Color EXPOSED_CELL_BACKGROUND_COLOR = new Color(196,164,132);
  // colors used when displaying the getStateStr() String
  private static final Color EXPOSED_CELL_FOREGROUND_COLOR_MAP[] = {Color.lightGray, Color.blue, Color.green, Color.cyan, Color.yellow, 
                                           Color.orange, Color.pink, Color.magenta, Color.red, Color.red};

  
  // holds the "number of mines in perimeter" value for each Final.MyJButton
  private static final int MINEGRID_ROWS = 10;
  private static final int MINEGRID_COLS = 10;
  private int[][] mineGrid = new int[MINEGRID_ROWS][MINEGRID_COLS]; // gird col and row

  private static final int NO_MINES_IN_PERIMETER_MINEGRID_VALUE = 0; // value for no mine
  private static final int ALL_MINES_IN_PERIMETER_MINEGRID_VALUE = 8;
  private static final int IS_A_MINE_IN_MINEGRID_VALUE = 9;
  
  private boolean running = true;

  public void setActualMinesLeft(int actualMinesLeft) {
    MineSweapPart.actualMinesLeft = actualMinesLeft;
  }

  public void setGuessedMinesLeft(int guessedMinesLeft) {
    MineSweapPart.guessedMinesLeft = guessedMinesLeft;
  }

  public MineSweapPart()
  {
    this.setGuessedMinesLeft(TOTAL_MINES);
    this.setActualMinesLeft(TOTAL_MINES);

    this.setTitle("MineSweap                                                         " +
                  MineSweapPart.guessedMinesLeft +" guess left");
    this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    this.setResizable(false);
    this.setLayout(new GridLayout(MINEGRID_ROWS, MINEGRID_COLS, 0, 0));
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // set the grid of MyJbuttons
    //this.createContents();


    
    // place MINES number of mines in sGrid and adjust all of the "mines in perimeter" values
    this.setMines();

    this.createContents();
    
    this.setVisible(true);
  }

  public void createContents()
  {
    for (int mgr = 0; mgr < mineGrid.length; ++mgr)
    {  
      for (int mgc = 0; mgc < mineGrid[mgr].length; ++mgc)
      {  
        // set sGrid[mgr][mgc] entry to 0 - no mines in it's perimeter
        //this.mineGrid[mgr][mgc] = NO_MINES_IN_PERIMETER_MINEGRID_VALUE;
        
        // create a Final.MyJButton that will be at location (mgr, mgc) in the GridLayout
        //MyJButton btn = new MyJButton(Integer.toString(mineGrid[mgr][mgc]), mgr, mgc);
        MyJButton btn = new MyJButton(INITIAL_CELL_TEXT, mgr, mgc);
        
        // register the event handler with this MyJbutton
        btn.addActionListener(new MyListener());
        btn.setBackground(new Color(132, 192, 17));
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(new Color(160,82,45)));
//        btn.setIcon(icon);

        
        // add the Final.MyJButton to the GridLayout collection
        this.add(btn);
      }  
    }
  }


  // begin nested private class
  private class MyListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      if ( running )
      {
        // used to determine if ctrl or alt key was pressed at the time of mouse action
        int mod = event.getModifiers();
        MyJButton mjb = (MyJButton)event.getSource();

        
        // is the MyJbutton that the mouse action occurred is flagged
        //boolean flagged = mjb.getText().equals(MineSweapPart.UNEXPOSED_FLAGGED_CELL_TEXT);
        boolean flagged =  mjb.getDesc().equals(MineSweapPart.UNEXPOSED_FLAGGED_CELL_TEXT);

        
        // is the MyJbutton that the mouse action occurred is already exposed
        boolean exposed = mjb.getBackground().equals(EXPOSED_CELL_BACKGROUND_COLOR);
       
        // flag a cell : ctrl + left click
        if ( !flagged && !exposed && (mod & ActionEvent.CTRL_MASK) != 0 && MineSweapPart.guessedMinesLeft > 0 )
        {
          //mjb.setText(MineSweapPart.UNEXPOSED_FLAGGED_CELL_TEXT);
          mjb.setDesc(MineSweapPart.UNEXPOSED_FLAGGED_CELL_TEXT);
          mjb.setIcon(MineSweapPart.UNEXPOSED_FLAGGED_CELL_ICON);
          --MineSweapPart.guessedMinesLeft;
          
          // if the MyJbutton that the mouse action occurred in is a mine
          if ( mineGrid[mjb.ROW][mjb.COL] == IS_A_MINE_IN_MINEGRID_VALUE )
          {
            // what else do you need to adjust? we need to decrease the actualMineLeft by 1
            // could the game be over? when actualMineLeft = 0, game over, you win
            --MineSweapPart.actualMinesLeft;
            System.out.println("actual mine: " + actualMinesLeft);
            Control.msg_static.setText(Integer.toString(actualMinesLeft));

            // Check if acutalMineLeft is 0, if true, game win
            if (MineSweapPart.actualMinesLeft == 0) {
              JOptionPane.showMessageDialog(null, "You win! You've found all " + TOTAL_MINES + " mines");
              running = false;
            }

          }
          setTitle("MineSweap" + MineSweapPart.guessedMinesLeft +" Mines left");

        }
       
        // unflag a cell : alt + left click
        else if ( flagged && !exposed && (mod & ActionEvent.ALT_MASK) != 0 && MineSweapPart.guessedMinesLeft >= 0 && MineSweapPart.guessedMinesLeft < TOTAL_MINES)
        {
          //mjb.setText(INITIAL_CELL_TEXT);
          mjb.setDesc(INITIAL_CELL_TEXT);
          mjb.setIcon(null);
          ++MineSweapPart.guessedMinesLeft;
          
          // if the MyJbutton that the mouse action occurred in is a mine
          if ( mineGrid[mjb.ROW][mjb.COL] == IS_A_MINE_IN_MINEGRID_VALUE )
          {
            // what else do you need to adjust?
            // we need to increase the atucalMineLeft By one
            ++MineSweapPart.actualMinesLeft;
            System.out.println("actual mine: " + actualMinesLeft);
            Control.msg_static.setText(Integer.toString(actualMinesLeft));

            // could the game be over?
            // In my program, the user can only add flags less than or equal to the number of total mines,
            // so it's impossible for user to win when they remove a flag.
          }
          setTitle("MineSweap" + MineSweapPart.guessedMinesLeft +" Mines left");
        }
     
        // expose a cell : left click
        else if ( !flagged && !exposed && (mod & ActionEvent.CTRL_MASK) == 0 && (mod & ActionEvent.ALT_MASK) == 0)
        {
          exposeCell(mjb);
        }  
      }
    }
    
    public void exposeCell(MyJButton mjb)
    {
      if ( !running )
        return;
      
      // expose this Final.MyJButton
      mjb.setBackground(EXPOSED_CELL_BACKGROUND_COLOR);
      mjb.setForeground(EXPOSED_CELL_FOREGROUND_COLOR_MAP[mineGrid[mjb.ROW][mjb.COL]]);
      mjb.setText(getGridValueStr(mjb.ROW, mjb.COL));
      
      // if the Final.MyJButton that was just exposed is a mine
      if ( mineGrid[mjb.ROW][mjb.COL] == IS_A_MINE_IN_MINEGRID_VALUE )
      {  
        // what else do you need to adjust?
        // could the game be over? game over
        //return;
        // expose all the mines after losing
        for (int i = 0; i < mineGrid.length; i++) {
          for (int j = 0; j < mineGrid[i].length; j++) {
           if(mineGrid[i][j] == 9) {
             int linearIndex = (i *  MINEGRID_COLS) + j;
             MyJButton mine = (MyJButton) (mjb.getParent().getComponent(linearIndex));
             mine.setBackground(EXPOSED_CELL_BACKGROUND_COLOR);
             mine.setForeground(EXPOSED_CELL_FOREGROUND_COLOR_MAP[mineGrid[mine.ROW][mine.COL]]);
             mine.setText(getGridValueStr(mine.ROW, mine.COL));
           }
          }
        }

        JOptionPane.showMessageDialog(null, "Sorry, you lost!");
        running = false;
      }
      
      // if the Final.MyJButton that was just exposed has no mines in its perimeter
      if ( mineGrid[mjb.ROW][mjb.COL] == NO_MINES_IN_PERIMETER_MINEGRID_VALUE )
      {

        // Loop thorough all 8 cells of current cell's perimeter
        for (int i = 0; i < 8; ++i) {
          // initialize r and c to -1 to aviod the cell with (0,0) always get triggered
          int r = -1;
          int c = -1;

          if (i == 0) {
            // case 1: (r-1, c-1)
            r = (mjb.ROW - 1) >= 0 ? mjb.ROW - 1 : -1;
            c = (mjb.COL - 1) >= 0 ? mjb.COL - 1 : -1;
          } else if (i == 1) {
            // case 2: (r-1, c)
            r = (mjb.ROW - 1) >= 0 ? mjb.ROW - 1 : -1;
            c = mjb.COL;
          } else if(i == 2) {
            // case 3: (r-1, c+1)
            r = (mjb.ROW - 1) >= 0 ? mjb.ROW - 1 : -1;
            c = (mjb.COL + 1) < MINEGRID_COLS ?  (mjb.COL + 1) : -1;
          } else if (i == 3) {
            // case 4: (r, c-1);
            r = mjb.ROW;
            c = (mjb.COL - 1) >= 0 ?  (mjb.COL - 1) : -1;
          } else if (i == 4) {
            // case 5: (r, c+1)
            r = mjb.ROW;
            c = (mjb.COL + 1) < MINEGRID_COLS ?  (mjb.COL + 1) : -1;
          } else if (i == 5) {
            //case 6: (r+1, c-1)
            r = (mjb.ROW + 1) < MINEGRID_ROWS ? (mjb.ROW + 1) : -1;
            c = (mjb.COL - 1) >= 0 ? mjb.COL - 1 : -1;
          } else if (i == 6) {
            //case 7: (r+1, c)
            r = (mjb.ROW + 1) < MINEGRID_ROWS ? (mjb.ROW + 1) : -1;
            c = mjb.COL;
          } else if (i == 7) {
            //case 8: (r+1, c+1)
            r = (mjb.ROW + 1) < MINEGRID_ROWS ? (mjb.ROW + 1) : -1;
            c = (mjb.COL + 1) < MINEGRID_COLS ?  (mjb.COL + 1) : -1;
          }

          if (r != -1 && c != -1) {
            // Hint: MyJButton mjbtn = (MyJButton)mjb.getParent().getComponent(index);
            // where index is a linearized version of a row, col index pair
            int linearIndex = (r *  MINEGRID_COLS) + c;
            MyJButton btn = (MyJButton) (mjb.getParent().getComponent(linearIndex));

            // Check if flagged and exposed
            boolean flagged = btn.getText().equals(MineSweapPart.UNEXPOSED_FLAGGED_CELL_TEXT);
            boolean exposed = btn.getBackground().equals(EXPOSED_CELL_BACKGROUND_COLOR);

            // expose btn if not flagged and exposed
            if (!flagged && !exposed) {
              // recursion method
              exposeCell(btn);
            }
          }
        }
      }
    }
  }
  // end nested private class


//  public static void main(String[] args)
//  {
//    new MineSweapPart();
//  }

  
  //************************************************************************************************

  // place MINES number of mines in sGrid and adjust all of the "mines in perimeter" values
  private void setMines()
  {
    //Generate 8 random numbers for 1-D array
    Set<Integer> set = new HashSet<Integer>();

    while (set.size() < TOTAL_MINES) {
      set.add(new Random().nextInt(MINEGRID_ROWS * MINEGRID_COLS));
    }

    Integer[] randomNumbers = set.toArray(new Integer[set.size()]);

    for (Integer number: randomNumbers) {
      // convert 1-D array to 2-D array index and set the mine
      int r = number / MINEGRID_COLS;
      int c = number % MINEGRID_COLS;
      mineGrid[r][c] = 9;
    }



    // Iterate each cell and check its perimeter
    for (int r = 0; r < mineGrid.length; r++) {
      for (int c = 0; c < mineGrid[r].length; c++) {
//        System.out.print(mineGrid[r][c] + " ");
//        if(c ==  MINEGRID_COLS - 1) {
//          System.out.println();
//        }

        if (mineGrid[r][c] != 9) {

          // case 1: (r-1, c-1)
          int rperimeter = (r - 1) >= 0 && (r - 1) < mineGrid.length ? (r - 1) : -1;
          int cperimeter = (c - 1) >= 0 && (c - 1) <= mineGrid[r].length ? (c - 1) : -1;

          if (rperimeter != -1 && cperimeter != -1) {
            //System.out.println(mineGrid[rperimeter][cperimeter]);
            if (mineGrid[rperimeter][cperimeter] == 9) {
              ++mineGrid[r][c];
            }
          }

          // case 2: (r-1, c)
          cperimeter = c;

          if(rperimeter != -1) {
            if (mineGrid[rperimeter][cperimeter] == 9) {
              ++mineGrid[r][c];
            }
          }

          // case 3: (r-1, c+1)
          cperimeter = (c + 1) < mineGrid[r].length ? (c + 1) : -1;

          if(rperimeter != -1 && cperimeter != -1) {
            if (mineGrid[rperimeter][cperimeter] == 9) {
              ++mineGrid[r][c];
            }
          }

          // case 4: (r, c-1)
          rperimeter = r;
          cperimeter = (c - 1) >= 0 && (c - 1) < mineGrid[r].length ? (c - 1) : -1;

          if(rperimeter != -1 && cperimeter != -1) {
            if (mineGrid[rperimeter][cperimeter] == 9) {
              ++mineGrid[r][c];
            }
          }

          // case 5: (r, c+1)
          cperimeter = (c + 1) < mineGrid[r].length ? (c + 1) : -1;
          if (cperimeter != -1) {
            if (mineGrid[rperimeter][cperimeter] == 9) {
              ++mineGrid[r][c];
            }
          }

          // case 6: (r+1, c-1)
          rperimeter = (r + 1) < mineGrid.length ? (r + 1) : -1;
          cperimeter = (c - 1) >= 0 && (c - 1) < mineGrid[r].length ? (c - 1) : -1;

          if(rperimeter != -1 && cperimeter != -1) {
            if (mineGrid[rperimeter][cperimeter] == 9) {
              ++mineGrid[r][c];
            }
          }

          // case 7: (r+1, c)
          cperimeter = c;

          if(rperimeter != -1) {
            if (mineGrid[rperimeter][cperimeter] == 9) {
              ++mineGrid[r][c];
            }
          }

          //case 8: (r+1, c+1)
          cperimeter = (c + 1) < mineGrid[r].length ? (c + 1) : -1;

          if(rperimeter != -1 && cperimeter != -1) {
            if (mineGrid[rperimeter][cperimeter] == 9) {
              ++mineGrid[r][c];
            }
          }

        }
      }
    }

    // Show Grid Map in console
    for (int i = 0; i < mineGrid.length; i++) {
      for (int j = 0; j < mineGrid[i].length; j++) {

        System.out.print(mineGrid[i][j] + " ");
        if(j ==  MINEGRID_COLS - 1) {
          System.out.println();
        }
      }
    }
  }
  
  private String getGridValueStr(int row, int col)
  {
    // no mines in this MyJbutton's perimeter
    if ( this.mineGrid[row][col] == NO_MINES_IN_PERIMETER_MINEGRID_VALUE )
      return INITIAL_CELL_TEXT;
    
    // 1 to 8 mines in this Final.MyJButton's perimeter
    else if ( this.mineGrid[row][col] > NO_MINES_IN_PERIMETER_MINEGRID_VALUE && 
              this.mineGrid[row][col] <= ALL_MINES_IN_PERIMETER_MINEGRID_VALUE )
      return "" + this.mineGrid[row][col];
    
    // this Final.MyJButton in a mine
    else // this.mineGrid[row][col] = IS_A_MINE_IN_GRID_VALUE
      return MineSweapPart.EXPOSED_MINE_TEXT;
  }
  
}
