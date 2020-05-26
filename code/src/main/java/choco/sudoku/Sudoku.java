package choco.sudoku;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

/**
 * Choco implementation of Sudoku.
 */
public class Sudoku
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
     * Main method.
     *
     * @param args jvm args
     * @throws ContradictionException in case of propagation fail
     */
    public static void main(final String[] args) throws ContradictionException
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
        // int[][] mySudokuGrid = GRID_HARD;
        // int[][] mySudokuGrid = MONSTER_GRID;
        int[][] mySudokuGrid = GRID_DIABOLIK;

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
                    // this cell will contain value between lowerbound (1) to upperboud (9)
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
            System.arraycopy(t[i], 0, columns[i], 0, n2);

            // Get line array
            lines[i] = t[i];
        }

        // *** SET CONSTRAINTS ON REGIONS ***
        // regions can be numbered (r) from 0 to n2 (excluded)
        // Given my region
        IntVar[][] r = new IntVar[n2][n2];

        // for a region r you can do 2 nested loops on i and j
        for (int x = 0; x < n2; x++)
        {
            IntVar[] block = new IntVar[n2];

            // Set up a counter
            int counter = 0;

            // region r: first coord i0 = r / n * n
            int i0 = x / n * n;
            // and j0 = r % n * n
            int j0 = x / n * n;

            // i from i0 to i0+n (excluded)
            for (int i = i0; i < i0 + n ; i++)
            {
                // and j from j0 to j0+n (excluded)
                for (int j = j0; j < j0 + n; j++)
                {
                    block[counter] = t[i][j];
                    counter++;
                }
                r[x] = block;
            }
        }

        // Say to choco to use differents values for each vars because of sudoku's rules
        for (int i = 0; i < n2; i++)
        {
            model.allDifferent(lines[i]).post();
            model.allDifferent(columns[i]).post();
            model.allDifferent(r[i]).post();
        }

        // Tell to solver to propagate constraints in order to reach endpoint
        solver.propagate();

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

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
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
