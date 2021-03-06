package choco.sudoku;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;


/**
 * Choco implementation of Sudoku.
 */
public class SudokuBis
{
    /**
     * Grid with difficulty "hard".
     */
    static int[][] GRID_HARD = { // hard
        {0, 0, 0, 2, 0, 0, 0, 3, 0},
        {0, 6, 0, 0, 8, 0, 9, 0, 4},
        {0, 0, 8, 4, 0, 7, 0, 0, 0},
        {1, 8, 0, 0, 0, 0, 0, 0, 3},
        {0, 0, 6, 0, 1, 0, 2, 0, 0},
        {2, 0, 0, 0, 0, 0, 0, 9, 5},
        {0, 0, 0, 1, 0, 8, 4, 0, 0},
        {4, 0, 7, 0, 3, 0, 0, 5, 0},
        {0, 1, 0, 0, 0, 4, 0, 0, 0}};

    /**
     * Grid with difficulty "diabolik".
     */
    static int[][] GRID_DIABOLIK = { // very hard (diabolik)
        {0, 0, 9, 0, 0, 0, 0, 4, 0},
        {7, 5, 0, 0, 0, 0, 0, 9, 0},
        {4, 3, 0, 0, 9, 1, 0, 0, 0},
        {0, 0, 2, 5, 0, 0, 1, 8, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 7, 5, 0, 0, 3, 6, 0, 0},
        {0, 0, 0, 2, 6, 0, 0, 7, 5},
        {0, 6, 0, 0, 0, 0, 0, 1, 3},
        {0, 8, 0, 0, 0, 0, 9, 0, 0}};

    /**
     * Grid with 16x16
     */
    static int[][] MONSTER_GRID = {
            {0,3,0,0,0,4,12,0,0,14,9,0,0,0,13,0},
            {5,4,12,0,0,10,9,0,0,8,15,0,0,2,16,1},
            {0,0,10,9,0,13,0,1,12,0,2,0,6,4,0,0},
            {0,0,7,2,3,14,0,5,6,0,16,11,9,10,0,0},
            {9,0,0,0,5,0,1,0,0,6,0,8,0,0,0,10},
            {0,0,0,0,0,0,10,16,4,7,0,0,0,0,0,0},
            {0,1,0,11,0,0,0,0,0,0,0,0,4,0,5,0},
            {16,0,15,0,0,0,11,6,14,3,0,0,0,1,0,2},
            {2,0,14,0,0,0,4,13,8,10,0,0,0,5,0,16},
            {0,9,0,12,0,0,0,0,0,0,0,0,8,0,1,0},
            {0,0,0,0,0,0,14,15,16,11,0,0,0,0,0,0},
            {10,0,0,0,8,0,7,0,0,1,0,5,0,0,0,15},
            {0,0,4,1,11,8,0,12,10,0,14,9,15,16,0,0},
            {0,0,3,8,0,16,0,9,5,0,1,0,2,6,0,0},
            {14,16,9,0,0,7,6,0,0,2,8,0,0,12,3,13},
            {0,15,0,0,0,2,5,0,0,16,6,0,0,0,4,0}
    };

    /**
     * Grid with 25x25
     */
    static int[][] GOD_LIKE = {
            {23,0,20,8,15,0,21,6,17,0,0,0,0,0,0,24,19,0,0,0,12,0,0,0,2},
            {0,0,14,3,11,0,16,2,0,13,0,0,0,17,0,0,15,0,18,0,0,0,10,0,0},
            {0,0,0,4,7,14,0,0,0,0,0,0,1,0,0,8,0,3,0,0,0,11,0,22,24},
            {0,0,0,6,0,23,0,0,0,0,0,0,0,19,0,10,0,0,0,25,0,0,0,0,0},
            {10,0,9,0,13,0,8,15,19,0,24,0,0,22,6,0,0,11,12,0,0,18,4,0,20},
            {0,3,0,0,10,0,25,0,0,16,0,0,4,21,9,22,0,0,8,20,0,12,0,7,17},
            {20,25,8,1,18,19,24,0,9,0,0,0,0,0,22,0,14,0,2,0,0,0,3,11,0},
            {16,0,23,0,22,0,0,0,18,4,17,0,0,10,0,7,0,0,15,5,0,0,0,24,0},
            {21,0,5,0,19,12,0,0,0,0,11,8,0,20,0,0,0,9,0,16,15,0,0,0,18},
            {0,0,7,15,0,0,5,0,10,17,3,0,0,0,0,21,0,6,0,19,0,22,14,0,0},
            {18,0,0,0,0,0,0,0,23,0,2,10,0,5,0,20,3,0,21,1,0,0,10,0,0},
            {0,23,21,2,5,0,6,0,15,0,19,0,12,16,0,0,0,0,24,7,0,9,0,8,0},
            {0,0,6,20,0,0,0,0,0,5,9,0,24,7,0,0,13,0,23,15,0,16,19,0,0},
            {0,0,0,0,0,0,19,0,2,4,0,1,0,18,0,3,16,0,2,14,11,0,4,17,0,15},
            {0,0,0,10,0,0,12,22,0,0,6,0,25,0,13,4,18,0,0,0,1,0,0,2,0},
            {0,2,0,0,0,5,23,0,0,22,0,0,0,0,0,0,9,25,0,0,21,1,0,0,11},
            {0,0,0,21,0,18,11,0,0,0,0,0,0,13,2,5,0,17,0,6,0,0,25,0,16},
            {0,0,0,17,4,0,0,0,0,25,0,15,16,8,19,0,0,0,0,21,0,23,0,14,22},
            {0,0,0,0,0,0,0,0,0,15,0,6,0,0,18,0,8,7,0,12,0,0,10,0,0},
            {0,0,18,22,14,13,0,24,6,20,0,3,7,0,0,11,0,0,0,0,0,0,0,12,0},
            {0,0,1,7,0,0,0,8,13,0,0,0,20,0,0,0,0,5,0,0,17,0,22,16,9},
            {0,0,12,13,0,2,22,25,7,0,0,0,0,1,0,19,0,0,0,14,0,3,20,18,23},
            {15,10,0,0,6,3,14,4,0,0,0,0,23,0,0,12,0,0,11,18,5,2,0,19,0},
            {0,19,4,0,3,17,9,0,11,0,22,0,0,2,15,0,0,0,0,0,0,14,6,0,1},
            {14,0,0,24,23,1,0,0,0,0,0,0,6,0,0,9,25,22,0,0,4,0,0,0,12},
    };

    /**
     * Main method.
     *
     * @param args jvm args
     * @throws ContradictionException in case of propagation fail
     */
    public static void main(final String[] args)
    {
        long startTime = System.nanoTime();
        // *** DECLARE THE MAIN ATTRIBUTES ***
        // Create a new choco model
        Model model = new Model("Sodoku");
        // Get solver
        Solver solver = model.getSolver();

        // size
        int n = 3;

        // n2 = n²
        int n2 = n * n;

        // choose grid
        // 9x9
         int[][] mySudokuGrid = GRID_HARD;
//         int[][] mySudokuGrid = GRID_DIABOLIK;

        // 16x16
        // int[][] mySudokuGrid = MONSTER_GRID;
        // 25x25
        // int[][] mySudokuGrid = GOD_LIKE;

        // Set boundaries values
        // We want values from 1 to 9 (0 is for blank case, i.e find a value to fit in)
        // My lower value is 1
        int lowerBound = 1;
        // My higher value is 9
        int upperBound = 9;


        // *** DECLARE THE MAIN MATRIX ***
        // model to think about :
        // Use IntVar[][] t = model.intVarMatrix(#lines, #columns, lower bound, upper bound);

        // Declare tab with n2*n2 dimension (n2 row and n2 column) and set values from 1 to 9
        IntVar[][] t = model.intVarMatrix("t", n2, n2, lowerBound, upperBound);

        // *** INITIALIZE t[i][j] according to grid[i][j] ***
        // Here we need to iterate over t[][]

        // For each t's lines
        for (int i = 0; i < n2; i++)
        {
            // For each t's columns
            for (int j = 0; j < n2; j++)
            {
                // in case of blank case (i.e, 0)
                if (mySudokuGrid[i][j] == 0)
                {
                    // contain value between lowerbound (1) and upperboud (9)
                    t[i][j] = model.intVar(lowerBound, upperBound);
                }
                else
                {
                    // Value is already displayed in my sudoku grid so i keep it
                    t[i][j] = model.intVar(mySudokuGrid[i][j]);
                }
            }
        }

        // *** SET CONSTRAINTS ON LINES ***
        IntVar[][] lines = new IntVar[n2][n2];
        // *** SET CONSTRAINTS ON COLUMNS ***
        IntVar[][] columns = new IntVar[n2][n2];

        // Feed arrays
        for (int i = 0; i < n2; i++)
        {
            // Get column array
            for (int j = 0; j < n2 ; j++)
            {
                columns[i][j] = t[i][j];
            }

            // Get line array
            lines[i] = t[i];
        }

        // *** SET CONSTRAINTS ON REGIONS ***
        // regions can be numbered (r) from 0 to n2 (excluded)
        // Given my region
        IntVar[][] regions = new IntVar[n2][n2];

        // for a region r you can do 2 nested loops on i and j
        for (int r = 0; r < n2; r++)
        {
            IntVar[] block = new IntVar[n2];

            // Set up a counter
            int counter = 0;

            // region r: first coord i0 = r / n * n
            int i0 = r / n * n;
            // and j0 = r % n * n
            int j0 = r % n * n;

            // i from i0 to i0+n (excluded)
            for (int i = i0; i < i0 + n ; i++)
            {
                // and j from j0 to j0+n (excluded)
                for (int j = j0; j < j0 + n; j++)
                {
                    block[counter] = t[i][j];
                    counter++;
                }
                regions[r] = block;
            }
        }

        // Say to choco to use differents values for each vars because of sudoku's rules
        for (int i = 0; i < n2; i++)
        {
            model.allDifferent(lines[i]).post();
            model.allDifferent(columns[i]).post();
            model.allDifferent(regions[i]).post();
        }

        // Tell to solver to propagate constraints in order to reach endpoint
        try
        {
            solver.propagate();
        }
        catch (ContradictionException ce)
        {
            System.out.println("Le problème n'a pas de solution !");
            System.exit(1);
        }

        // *** SOLVE ***
        // find solution
        Solution solution = solver.findSolution();


        // if solution is null, then we are doomed
        if (solution == null)
        {
            System.out.println("Damn, it's a K.O !");
        }
        // Victory, we go a solution here
        else
        {
            // Call helper method to display sudoku
            displaySudoku(n, t);

            solver.showShortStatisticsOnShutdown();

            // Add end time
            long endTime = System.nanoTime();
            // calcul duration
            long duration = (endTime - startTime);

            // print out
            System.out.println("");
            System.out.println("Solution found in : " + duration / 1000000 + " ms");
        }
    }

    /**
     * Helper method in charge of drawing.
     *
     * @param n number
     * @param t given IntVar array
     */
    private static void displaySudoku(final int n, final IntVar[][] t)
    {
        // *** Set up variables ***
        // n2 means n²
        int n2 = n * n;
        int width = String.valueOf(n2).length();

        // outprint title
        System.out.println("***************************************");
        System.out.println("****** SOLUTION FOR SUDOKU " + n2 + "x" + n2 + " ********");
        System.out.println("***************************************");

        System.out.println("");

        // StringBuilder
        StringBuilder s = new StringBuilder();

        // Iterate trought 0 to N
        for (int i = 0; i < n; i++)
        {
            // draw first corner
            s.append("¤");
            // draw line
            for (int j = 0; j < n * 2 * width + 1; j++)
            {
                s.append("¤");
            }
        }
        // add end corner
        s.append("¤");

        // Iterate trought
        for (int i = 0; i < n2; i++)
        {
            if (i % n == 0)
            {
                System.out.println(" " + s);
            }
            for (int j = 0; j < n2; j++)
            {
                if (j % n == 0)
                {
                    System.out.print(" |");
                }
                System.out.printf(" %" + width + "d", t[i][j].getValue());
            }
            System.out.println(" |");
        }
        System.out.println(" " + s);
    }

}
