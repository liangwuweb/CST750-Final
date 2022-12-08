package Demo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MyClass {

    private static int[][] mineGrid = new int[4][4];
    private static final int MINEGRID_ROWS = 4;
    private static final int MINEGRID_COLS = 4;

    public static void main(String[] args) {

    //for(int i = 0; i< 8; i++){
        Set<Integer> set = new HashSet<Integer>();

        while (set.size() < 8) {
            set.add(new Random().nextInt(16));
        }
//        int cardIndex = new Random().nextInt(16);
        Integer[] randomNumbers = set.toArray(new Integer[set.size()]);
        for (Integer number: randomNumbers) {
            //System.out.println(number);
            int r = number / MINEGRID_COLS;
            int c = number % MINEGRID_COLS;
            mineGrid[r][c] = 1;
        }

        //int number = 7;

        for (int r = 0; r < mineGrid.length; r++) {
            for (int c = 0; c < mineGrid[r].length; c++) {
                System.out.print(mineGrid[r][c]);
                if(c ==  MINEGRID_COLS - 1) {
                    System.out.println();
                }
            }
        }
    //}
//        int r = -1;
//        int c = 0;
//
//        if(r == -1 || c == -1) {
//            System.out.println("do nothing");
//        }

        for(int i =0;i < 3; ++i) {
            int index = 0;
            if (i == 0) {
                index = 12;
            } else if (i == 1) {
                index = 36;
            } else if (i == 2) {
                index = 45;
            }
            System.out.println(index);
        }



    }
}