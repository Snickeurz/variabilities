package choco.sudoku;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

import java.util.concurrent.ThreadLocalRandom;

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
         generateSudoku();

        // *** DECLARE THE MAIN ATTRIBUTES ***
        // Create a new choco model
        Model model = new Model("Sodoku");

        // declare sizes
        int n = 3;
        int n2 = n * n;

        // choose grid
        // 9x9
        int[][] mySudokuGrid = GRID_HARD;
        // int[][] mySudokuGrid = GRID_DIABOLIK;

        // 16x16
        // int[][] mySudokuGrid = MONSTER_GRID;
        // 25x25
        // int[][] mySudokuGrid = GOD_LIKE;

        // Set boundaries values
        // We want values from 1 to 9 (0 is for blank case, i.e find a value to fit in)
        // My lower value is 1
        int lowerBound = 1;
        // My higher value is 9
        int upperBound = n2;

        // solve(model, mySudokuGrid, n, n2, lowerBound, upperBound);
    }

    /**
     * Helper method for refactor.
     *
     * @param model current model
     * @param mySudokuGrid current sudoku grid
     * @param n size
     * @param n2 size²
     * @param lowerBound min value
     * @param upperBound highest value
     */
    public static void solve(final Model model, final int[][] mySudokuGrid, final int n, final int n2, final int lowerBound, final int upperBound)
    {
        // Start time
        long startTime = System.nanoTime();

        // Declare tab with n2*n2 dimension (n2 row and n2 column)
        IntVar[][] t = new IntVar[n2][n2];

        // *** INITIALIZE t[i][j] according to grid[i][j] ***
        // Here we need to iterate over t[][]
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                // If my current cell is > 0, then i pick the current value
                if (mySudokuGrid[i][j] > 0)
                {
                    t[i][j] = model.intVar(mySudokuGrid[i][j]);
                }
                // my current cell is equal to 0, need a domain from lower to upper bound
                else
                {
                    t[i][j] = model.intVar("X", lowerBound, upperBound);
                }
            }
        }


        // *** SET CONSTRAINTS ON LINES ***
        IntVar[] lines = new IntVar[n2];
        // *** SET CONSTRAINTS ON COLUMNS ***
        IntVar[] cols = new IntVar[n2];
        // *** SET CONSTRAINTS ON REGION ***
        IntVar[] region = new IntVar[n2];
        for (int i = 0; i < n2; i++)
        {
            // Feed constraints
            for (int j = 0; j < n2; j++)
            {
                lines[j] = t[i][j];
                cols[j] = t[j][i];
            }
            // Say to choco to use differents values for each vars because of sudoku's rules
            model.allDifferent(lines).post();
            model.allDifferent(cols).post();
        }


        // for a region r you can do 2 nested loops on i and j
        for (int r = 0; r < n2; r++)
        {
            // Set up a counter
            int counter = 0;

            // region r: first coord i0 = r / n * n
            int i0 = r / n * n;
            // and j0 = r % n * n
            int j0 = r % n * n;

            // i from i0 to i0+n (excluded)
            for (int i = 0; i < n ; i++)
            {
                // and j from j0 to j0+n (excluded)
                for (int j = 0; j < n; j++)
                {
                    region[counter] = t[i + i0][j + j0];
                    counter++;
                }
            }
            model.allDifferent(region).post();
        }

        // Get solver
        Solver solver = model.getSolver();

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
     *  Generate sudoku grid 9x9.
     *
     *  Steps :
     *  <p>
     *      1) Generate grid of 0.
     *         Then put random number in each cell.
     *  </p>
     *
     * <p>
     *     2) Solv the grid
     *        Keep first solution.
     * </p>
     *
     * <p>
     * 3) iterate trought first solution and remove random number, one by one.
     * When number is removed, re solve the model. If number of solution > 1 rebind the number.
     * Redo process until > 1.
     * </p>
     *
     */
    public static void generateSudoku()
    {
        System.out.println("Generating a sudoku 9x9");

        // Create a new choco model
        Model model = new Model("generatedSodoku");

        // declare sizes
        int n = 3;
        int n2 = n * n;
        int emptyCell = 0;

        // Create a 9x9 grid
        IntVar[][] myGrid = new IntVar[n2][n2];

        // Create empty grid 9x9
        for (int x = 0; x < n2; x++)
        {
            for (int y = 0; y < n2; y++)
            {
                myGrid[x][y] = model.intVar(emptyCell);
            }
        }

        // display 9x9 with all values to 0
        displaySudoku(n, myGrid);
    }

    /**
     * 1 )
     *
     * génerer grille de 0
     *
     * mettre un nb aléatoire dans une case
     *
     *
     * 2)
     *
     * Solver cette grille
     *
     * x --> garder premiere solution
     *
     * 3)
     *
     * iterate trought x et retirer nb aleatoire , un seul par un seul
     *
     * quand on enleve numero, on refait un solve, si solution > 1 bah alors on remet le chiffre
     * sinon on continue on refait
     */

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
